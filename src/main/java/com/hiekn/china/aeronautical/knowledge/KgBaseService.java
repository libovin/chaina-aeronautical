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
import com.hiekn.china.aeronautical.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
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
    public static final String ATTR_SUFFIX = "_attribute_definition";
    public static final String DOMAIN_VALUE = "domain_value";
    public static final String PARENT = "parent";
    public static final String OBJ_ID = "_id";
    public static final String MEANING_TAG = "meaning_tag";
    public static final String CONCEPT_ID = "concept_id";
    public static final String ENTITY_TYPE = "entity_type";
    public static final String ENTITY_ID = "entity_id";
    public static final String INS_ID = "ins_id";
    public static final String ID = "id";
    public static final String META_DATA = "meta_data";
    public static final String ATTR_VALUE = "attr_value";
    public static final String ATTR_ID = "attr_id";

    @PostConstruct
    private void init() {
        List<KgDbName> list = kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
        for (KgDbName kgDbName : list) {
            this.kgNameMap.put(kgDbName.getKgName(), kgDbName.getDbName());
        }
    }

    public Map<String, String> getKgNameMap() {
        return this.kgNameMap;
    }

    private List<AttrDefine> attrDefineList(String kgName, String schema) {
        String collectionName = this.kgNameMap.get(kgName) + ATTR_SUFFIX;
        return kgAttrDefineMongoTemplate.find(query(where(DOMAIN_VALUE).is(schemaId(kgName, schema))), AttrDefine.class, collectionName);
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
        List<ParentSon> parenSonList = mongoTemplate.find(new Query(where(PARENT).is(0)), ParentSon.class);
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
        Query query = Query.query(where(OBJ_ID).in(list))
                .addCriteria(where(MEANING_TAG).is(schema));
        return mongoTemplate.findOne(query, BasicInfo.class);
    }

    private List<AttributeString> attrString(String kgName, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where(ENTITY_ID).is(id)), AttributeString.class);
    }

    private List<AttributeString> attrString(String kgName, String schema, Pageable pageable) {
        MongoTemplate mongoTemplate = template(kgName);
        List<AggregationOperation> operationList = new ArrayList<>();
        operationList.add(match(where(CONCEPT_ID).is(schemaId(kgName, schema))));
        if (pageable != null) {
            operationList.add(skip((long) (pageable.getPageSize() * (pageable.getPageNumber() - 1))));
            operationList.add(limit(pageable.getPageSize()));
        }
        operationList.add(lookup("attribute_string", "ins_id", "entity_id", "attribute_string"));
        operationList.add(unwind("attribute_string"));
        operationList.add(project()
                .and("attribute_string.entity_id").as("entity_id")
                .and("attribute_string.entity_type").as("entity_type")
                .and("attribute_string.attr_id").as("attr_id")
                .and("attribute_string.attr_value").as("attr_value")
                .andExclude("_id")
        );
        Aggregation aggregation = newAggregation(operationList);
        AggregationResults<AttributeString> aggregate = mongoTemplate.aggregate(aggregation, ConceptInstance.class, AttributeString.class);
        return aggregate.getMappedResults();
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
        List<AttributeString> attributeString = attrString(kgName, schema, pageable);
        for (AttributeString attribute : attributeString) {
            Map<String, Object> map = entityIdMap.computeIfAbsent(attribute.getEntityId(), (k) -> new HashMap<>());
            AttrDefine ad = attributeMap.get(attribute.getAttrId());
            map.put(ad.getAlias(), attribute.getAttrValue());
        }
        return entityIdMap;
    }

    public <T extends MarkError> List<T> findAll(String kgName, String schema, Class<T> clz) {
        return find(kgName, schema, null, clz);
    }

    public <T extends MarkError> T findOne(String kgName, String schema, Long id, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = getEntityMapById(kgName, schema, id);
        Map<String, Object> entityMap = entityIdMap.get(id);
        if (entityMap != null) {
            T entity = mapToEntity(entityMap, clz);
            if (entity != null) {
                entity.setId(id);
            }
            return entity;
        }
        return null;
    }

    public <T extends MarkError> List<T> find(String kgName, String schema, Pageable pageable, Class<T> clz) {
        Map<Long, Map<String, Object>> entityIdMap = getEntityMaps(kgName, schema, pageable);
        List<T> list = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Object>> entry : entityIdMap.entrySet()) {
            T entity = mapToEntity(entry.getValue(), clz);
            if (entity != null) {
                entity.setId(entry.getKey());
            }
            list.add(entity);
        }
        return list;
    }

    public Long count(String kgName, String schema) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = Query.query(where(CONCEPT_ID).is(schemaId(kgName, schema)));
        return mongoTemplate.count(query, ConceptInstance.class);
    }

    private Long maxId(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        Query query = new Query().with(new Sort(Sort.Direction.DESC, OBJ_ID)).limit(1);
        BasicInfo basicInfo = mongoTemplate.findOne(query, BasicInfo.class);
        return basicInfo.getId();
    }

    public <T extends MarkError> T insert(String kgName, String schema, T entity) {
        MongoTemplate mongoTemplate = template(kgName);
        Long entityId = maxId(kgName) + 1;
        entity.setId(entityId);
        Long conceptId = schemaId(kgName, schema);
        BeanMap beanMap = BeanMap.create(entity);
        String name = null;
        Map metaData = new HashMap<>();
        metaData.put(META_DATA + "_2", DateUtils.getCurrentDateTime());
        for (Object key : beanMap.keySet()) {
            Map<String, AttrDefine> map = attrDefineNameMap(kgName, schema);
            AttrDefine attrDefine = map.get(key);
            if (attrDefine != null) {
                Integer dataType = attrDefine.getDataType();
                Integer attrId = attrDefine.getId();
                String value = (String) beanMap.get(key);
                if (value != null) {
                    mongoTemplate.save(new AttributeSummary(entityId, attrId));
                    mongoTemplate.save(new AttributeString(entityId, attrId, conceptId, value, metaData));
                }
            }
            if ("name".equals(key)) {
                name = (String) beanMap.get(key);
            }
        }
        mongoTemplate.save(new BasicInfo(entityId, name, metaData));
        mongoTemplate.save(new EntityId(entityId, name, conceptId, metaData));
        mongoTemplate.save(new ConceptInstance(conceptId, entityId, metaData));
        return entity;
    }

    public <T extends MarkError> T modify(String kgName, String schema, Long entityId, T entity) {
        MongoTemplate mongoTemplate = template(kgName);
        Long conceptId = schemaId(kgName, schema);
        BeanMap beanMap = BeanMap.create(entity);
        for (Object key : beanMap.keySet()) {
            Map<String, AttrDefine> map = attrDefineNameMap(kgName, schema);
            AttrDefine attrDefine = map.get(key);
            if (attrDefine != null) {
                Integer dataType = attrDefine.getDataType();
                Integer attrId = attrDefine.getId();
                String value = beanMap.get(key) != null ? (String) beanMap.get(key) : "";
                Query query = Query.query(where(ENTITY_ID).is(entityId).and(ATTR_ID).is(attrId).and(ENTITY_TYPE).is(conceptId));
                Update update = Update.update(ATTR_VALUE, value);
                mongoTemplate.updateFirst(query, update, AttributeString.class);
            }
        }
        return entity;
    }

    public void delete(String kgName, String schema, Long id) {
        MongoTemplate mongoTemplate = template(kgName);
        mongoTemplate.remove(Query.query(where(ENTITY_ID).is(id).and(ENTITY_TYPE).is(schemaId(kgName, schema))), AttributeObject.class);
        mongoTemplate.remove(Query.query(where(ENTITY_ID).is(id).and(ENTITY_TYPE).is(schemaId(kgName, schema))), AttributeString.class);
        mongoTemplate.remove(Query.query(where(ENTITY_ID).is(id)), AttributeSummary.class);
        mongoTemplate.remove(Query.query(where(INS_ID).is(id).and(CONCEPT_ID).is(schemaId(kgName, schema))), ConceptInstance.class);
        mongoTemplate.remove(Query.query(where(ID).is(id).and(CONCEPT_ID).is(schemaId(kgName, schema))), EntityId.class);
        mongoTemplate.remove(Query.query(where(OBJ_ID).is(id)), BasicInfo.class);
    }

}
