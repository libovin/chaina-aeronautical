package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeObject;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeString;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeSummary;
import com.hiekn.china.aeronautical.knowledge.bean.database.BasicInfo;
import com.hiekn.china.aeronautical.knowledge.bean.database.ConceptInstance;
import com.hiekn.china.aeronautical.knowledge.bean.database.ParentSon;
import com.hiekn.china.aeronautical.knowledge.bean.define.AttrDefine;
import com.hiekn.china.aeronautical.knowledge.bean.define.KgDbName;
import com.hiekn.china.aeronautical.knowledge.config.MongoTemplateUtils;
import com.hiekn.china.aeronautical.model.base.MarkError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Pageable;
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

    private Map<String, Map<String, Map<Integer, AttrDefine>>> kgSchemaAttr = new ConcurrentHashMap<>();

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

    public List<KgDbName> findAll() {
        return kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
    }

    private Map<Integer, AttrDefine> attrDefine(String kgName, String schema) {
        Map<String, Map<Integer, AttrDefine>> kg = kgSchemaAttr.computeIfAbsent(kgName, (k) -> new HashMap<>());
        return kg.computeIfAbsent(schema, (k) -> {
            String collectionName = this.kgNameMap.get(kgName) + ATTR_SUFFIX;
            List<AttrDefine> attributeDefinitions = kgAttrDefineMongoTemplate.find(query(where("domain_value").is(schemaId(kgName, schema))), AttrDefine.class, collectionName);
            Map<Integer, AttrDefine> map = new HashMap<>();
            for (AttrDefine definition : attributeDefinitions) {
                map.put(definition.getId(), definition);
            }
            return map;
        });
    }

    private MongoTemplate template(String kgName) {
        String dbName = kgNameMap.get(kgName);
        return mongoTemplateUtils.template(dbName);
    }

    private BasicInfo getBaseInfo(String kgName, String schema, List<Long> list) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = Query.query(where("_id").in(list))
                .addCriteria(where("meaning_tag").is(schema));
        return mongoTemplate.findOne(query, BasicInfo.class);
    }

    private List<ParentSon> getParenSonList(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(where("parent").is(0)), ParentSon.class);
    }

    public List<AttributeObject> getAttributeObject(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), AttributeObject.class);
    }

    public List<Long> entityIds(String kgName, String schema, Pageable pageable) {
        MongoTemplate mongoTemplate = template(kgName);
        List<ConceptInstance> list = mongoTemplate.find(new Query(where("concept_id").is(schemaId(kgName,schema))).with(pageable), ConceptInstance.class);
        List<Long> entityIds = new ArrayList<>();
        for (ConceptInstance instance : list) {
            entityIds.add(instance.getInsId());
        }
        return entityIds;
    }

    public List<AttributeSummary> attributeSummary(String kgName, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(where("entity_id").is(id)), AttributeSummary.class);
    }

    public List<AttributeSummary> attributeSummary(String kgName, List<Long> ids) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(where("entity_id").in(ids)), AttributeSummary.class);
    }

    private List<AttributeString> attributeString(String kgName, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("entity_id").is(id)), AttributeString.class);
    }

    public List<AttributeString> attributeString(String kgName, List<Long> ids) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("entity_id").in(ids)), AttributeString.class);
    }

    private Long schemaId(String kgName, String schema) {
        Map<String, Long> map = kgSchemaIdMap.computeIfAbsent(kgName, (k) -> new HashMap<>());
        return map.computeIfAbsent(schema, (k) -> {
            List<ParentSon> parenSonList = getParenSonList(kgName);
            List<Long> idList = new ArrayList<>();
            for (ParentSon parentSon : parenSonList) {
                idList.add(parentSon.getSon());
            }
            BasicInfo basicInfo = getBaseInfo(kgName, schema, idList);
            return basicInfo.getId();
        });
    }

    private <T extends MarkError> T mapToEntity(Map<String, Object> map, Class<T> clz) {
        T entity = null;
        try {
            entity = clz.newInstance();
            BeanMap beanMap = BeanMap.create(entity);
            beanMap.putAll(map);
        } catch (Exception e) {
        }
        return entity;
    }

    private Map<Long, Map<String, Object>> findOne(String kgName, String schema, Long id) {
        Map<Long, Map<String, Object>> entityIdMap = new LinkedHashMap<>();
        Map<Integer, AttrDefine> attributeMap = attrDefine(kgName, schema);
        List<AttributeString> attributeString = attributeString(kgName, id);
        for (AttributeString attribute : attributeString) {
            Map<String, Object> map = entityIdMap.computeIfAbsent(id, (k) -> new HashMap<>());
            AttrDefine ad = attributeMap.get(attribute.getAttrId());
            map.put(ad.getAlias(), attribute.getAttrValue());
        }
        return entityIdMap;
    }

    private Map<Long, Map<String, Object>> find(String kgName, String schema, Pageable pageable) {
        Map<Long, Map<String, Object>> entityIdMap = new LinkedHashMap<>();
        Map<Integer, AttrDefine> attributeMap = attrDefine(kgName, schema);
        List<Long> ids = entityIds(kgName,schema,pageable);
        List<AttributeString> attributeString = attributeString(kgName, ids);
        for (AttributeString attribute : attributeString) {
            Map<String, Object> map = entityIdMap.computeIfAbsent(attribute.getEntityId(), (k) -> new HashMap<>());
            AttrDefine ad = attributeMap.get(attribute.getAttrId());
            map.put(ad.getAlias(), attribute.getAttrValue());
        }
        return entityIdMap;
    }

    public <T extends MarkError> T findOne(String kgName, String schema, Long id, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = findOne(kgName, schema, id);
        T entity = mapToEntity(entityIdMap.get(id), clz);
        if (entity != null) {
            entity.setId(id.toString());
        }
        return entity;
    }

    public <T extends MarkError> List<T> find(String kgName, String schema, Pageable pageable, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = find(kgName, schema, pageable);
        List<T> list = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Object>> entry : entityIdMap.entrySet()) {
            T entity = mapToEntity(entry.getValue(), clz);
            if (entity != null) {
                entity.setId(entry.getKey().toString());
            }
        }
        return list;
    }
}
