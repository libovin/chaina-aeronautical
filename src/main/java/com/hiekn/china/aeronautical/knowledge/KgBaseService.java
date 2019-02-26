package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeObject;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeString;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeSummary;
import com.hiekn.china.aeronautical.knowledge.bean.database.BasicInfo;
import com.hiekn.china.aeronautical.knowledge.bean.database.ConceptInstance;
import com.hiekn.china.aeronautical.knowledge.bean.database.EntityId;
import com.hiekn.china.aeronautical.knowledge.bean.database.ParentSon;
import com.hiekn.china.aeronautical.knowledge.bean.define.AttrDefine;
import com.hiekn.china.aeronautical.knowledge.bean.define.KgDbName;
import com.hiekn.china.aeronautical.knowledge.config.MongoTemplateUtils;
import com.hiekn.china.aeronautical.model.base.MarkError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KgBaseService {

    @Qualifier("kgAttrDefineMongoTemplate")
    @Autowired
    private MongoTemplate kgAttrDefineMongoTemplate;

    @Autowired
    private MongoTemplateUtils mongoTemplateUtils;

    private Map<String, String> kgNameMap = new ConcurrentHashMap<>();

    private Map<String, Map<String, Long>> kgSchemaIdMap = new ConcurrentHashMap<>();

    private Map<String, Map<String, Map<Integer, AttrDefine>>> kgSchemaAttrId = new ConcurrentHashMap<>();
    private Map<String, Map<String, Map<String, AttrDefine>>> kgSchemaAttrName = new ConcurrentHashMap<>();
    /**
     * 数据库概览定义结尾
     */
    private final static String ATTR_SUFFIX = "_attribute_definition";

    @PostConstruct
    void init() {
        List<KgDbName> list = kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
        for (KgDbName kgDbName : list) {
            this.kgNameMap.put(kgDbName.getKgName(), kgDbName.getDbName());
        }
    }

    private List<AttrDefine> attrDefineList(String kgName, String schema) {
        String collectionName = this.kgNameMap.get(kgName) + ATTR_SUFFIX;
        return kgAttrDefineMongoTemplate.find(query(where("domain_value").is(schemaId(kgName, schema))), AttrDefine.class, collectionName);
    }

    private MongoTemplate template(String kgName) {
        String dbName = kgNameMap.get(kgName);
        return mongoTemplateUtils.template(dbName);
    }

    private Map<Integer, AttrDefine> attrDefineIdMap(String kgName, String schema) {
        Map<String, Map<Integer, AttrDefine>> kg = kgSchemaAttrId.computeIfAbsent(kgName, (k) -> new HashMap<>());
        return kg.computeIfAbsent(schema, (k) -> {
            Map<Integer, AttrDefine> map = new HashMap<>();
            for (AttrDefine definition : attrDefineList(kgName, schema)) {
                map.put(definition.getId(), definition);
            }
            return map;
        });
    }

    private Map<String, AttrDefine> attrDefineNameMap(String kgName, String schema) {
        Map<String, Map<String, AttrDefine>> kg = kgSchemaAttrName.computeIfAbsent(kgName, (k) -> new HashMap<>());
        return kg.computeIfAbsent(schema, (k) -> {
            Map<String, AttrDefine> map = new HashMap<>();
            for (AttrDefine definition : attrDefineList(kgName, schema)) {
                map.put(definition.getAlias(), definition);
            }
            return map;
        });
    }

    private List<Long> schemaIds(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        List<ParentSon> parenSonList = mongoTemplate.find(new Query(where("parent").is(0)), ParentSon.class);
        List<Long> idList = new ArrayList<>();
        for (ParentSon parentSon : parenSonList) {
            idList.add(parentSon.getSon());
        }
        return idList;
    }

    private Long schemaId(String kgName, String schema) {
        Map<String, Long> map = kgSchemaIdMap.computeIfAbsent(kgName, (k) -> new HashMap<>());
        return map.computeIfAbsent(schema, (k) -> {
            List<Long> idList = schemaIds(kgName);
            BasicInfo basicInfo = getBaseInfo(kgName, schema, idList);
            return basicInfo.getId();
        });
    }

    private BasicInfo getBaseInfo(String kgName, String schema, List<Long> list) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = Query.query(where("_id").in(list))
                .addCriteria(where("meaning_tag").is(schema));
        return mongoTemplate.findOne(query, BasicInfo.class);
    }


    private List<Long> entityIds(String kgName, String schema, Pageable pageable) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = Query.query(where("concept_id").is(schemaId(kgName, schema))).with(pageable);
        List<ConceptInstance> list = mongoTemplate.find(query, ConceptInstance.class);
        List<Long> entityIds = new ArrayList<>();
        for (ConceptInstance instance : list) {
            entityIds.add(instance.getInsId());
        }
        return entityIds;
    }

    private List<AttributeString> attrString(String kgName, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("entity_id").is(id)), AttributeString.class);
    }

    private List<AttributeString> attrString(String kgName, List<Long> ids) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("entity_id").in(ids)), AttributeString.class);
    }

    private <T extends MarkError> T mapToEntity(Map<String, Object> map, Class<T> clz) {
        T entity = null;
        try {
            entity = clz.newInstance();
            BeanMap beanMap = BeanMap.create(entity);
            beanMap.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    private Map<Long, Map<String, Object>> getEntityMapById(String kgName, String schema, Long id) {
        Map<Long, Map<String, Object>> entityIdMap = new LinkedHashMap<>();
        Map<Integer, AttrDefine> attributeMap = attrDefineIdMap(kgName, schema);
        List<AttributeString> attributeString = attrString(kgName, id);
        for (AttributeString attribute : attributeString) {
            Map<String, Object> map = entityIdMap.computeIfAbsent(id, (k) -> new HashMap<>());
            AttrDefine ad = attributeMap.get(attribute.getAttrId());
            map.put(ad.getAlias(), attribute.getAttrValue());
        }
        return entityIdMap;
    }

    private Map<Long, Map<String, Object>> getEntityMaps(String kgName, String schema, Pageable pageable) {
        Map<Long, Map<String, Object>> entityIdMap = new LinkedHashMap<>();
        Map<Integer, AttrDefine> attributeMap = attrDefineIdMap(kgName, schema);
        List<Long> ids = entityIds(kgName, schema, pageable);
        List<AttributeString> attributeString = attrString(kgName, ids);
        for (AttributeString attribute : attributeString) {
            Map<String, Object> map = entityIdMap.computeIfAbsent(attribute.getEntityId(), (k) -> new HashMap<>());
            AttrDefine ad = attributeMap.get(attribute.getAttrId());
            map.put(ad.getAlias(), attribute.getAttrValue());
        }
        return entityIdMap;
    }

    public <T extends MarkError> T findOne(String kgName, String schema, Long id, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = getEntityMapById(kgName, schema, id);
        T entity = mapToEntity(entityIdMap.get(id), clz);
        if (entity != null) {
            entity.setId(id.toString());
        }
        return entity;
    }

    public <T extends MarkError> List<T> find(String kgName, String schema, Pageable pageable, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = getEntityMaps(kgName, schema, pageable);
        List<T> list = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Object>> entry : entityIdMap.entrySet()) {
            T entity = mapToEntity(entry.getValue(), clz);
            if (entity != null) {
                entity.setId(entry.getKey().toString());
            }
        }
        return list;
    }

    public Long count(String kgName, String schema) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = Query.query(where("concept_id").is(schemaId(kgName, schema)));
        return mongoTemplate.count(query, ConceptInstance.class);
    }

    private Long maxId(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id")).limit(1);
        BasicInfo basicInfo = mongoTemplate.findOne(query, BasicInfo.class);
        return basicInfo.getId();
    }

    public <T extends MarkError> void insert(String kgName, String schema, T entity) {
        MongoTemplate mongoTemplate = template(kgName);
        Long entityId = maxId(kgName) + 1;
        BeanMap beanMap = BeanMap.create(entity);
        for (Object key : beanMap.keySet()) {
            Map<String, AttrDefine> map = attrDefineNameMap(kgName, schema);
            AttrDefine attrDefine = map.get(key);
            System.out.println(key);
            if(attrDefine != null) {
                Integer integer = attrDefine.getDataType();
                Object value = beanMap.get(key);
            }
        }
    }

    public void delete(String kgName, String schema, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        mongoTemplate.remove(Query.query(where("entity_id").is(id).and("entity_type").is(schemaId(kgName, schema))), AttributeObject.class);
        mongoTemplate.remove(Query.query(where("entity_id").is(id).and("entity_type").is(schemaId(kgName, schema))), AttributeString.class);
        mongoTemplate.remove(Query.query(where("entity_id").is(id)), AttributeSummary.class);
        mongoTemplate.remove(Query.query(where("ins_id").is(id).and("concept_id").is(schemaId(kgName, schema))), ConceptInstance.class);
        mongoTemplate.remove(Query.query(where("id").is(id).and("concept_id").is(schemaId(kgName, schema))), EntityId.class);
        mongoTemplate.remove(Query.query(where("_id").is(id)), BasicInfo.class);
    }


    public List<AttributeObject> getAttributeObject(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), AttributeObject.class);
    }


    public List<AttributeSummary> attributeSummary(String kgName, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(where("entity_id").is(id)), AttributeSummary.class);
    }

    public List<AttributeSummary> attributeSummary(String kgName, List<Long> ids) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(where("entity_id").in(ids)), AttributeSummary.class);
    }
}
