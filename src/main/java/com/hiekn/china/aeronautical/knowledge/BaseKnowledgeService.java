package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeObject;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeSummary;
import com.hiekn.china.aeronautical.knowledge.bean.database.ConceptInstance;
import com.hiekn.china.aeronautical.knowledge.bean.define.AttributeDefinition;
import com.hiekn.china.aeronautical.knowledge.bean.database.AttributeString;
import com.hiekn.china.aeronautical.knowledge.bean.database.BasicInfo;
import com.hiekn.china.aeronautical.knowledge.bean.define.KgDbName;
import com.hiekn.china.aeronautical.knowledge.bean.database.ParentSon;
import com.hiekn.china.aeronautical.knowledge.config.MongoTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class BaseKnowledgeService {

    @Qualifier("kgAttrDefineMongoTemplate")
    @Autowired
    private MongoTemplate kgAttrDefineMongoTemplate;

    @Autowired
    private MongoTemplateUtils mongoTemplateUtils;

    private Map<String, String> kg2DbName;

    private Map<String, Map<Long, List<AttributeDefinition>>> cacheAttr;

    private static String ATTR_SUFFIX = "_attribute_definition";

    @PostConstruct
    void init() {
        this.kg2DbName = new ConcurrentHashMap<>();
        List<KgDbName> list = kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
        for (KgDbName kgDbName : list) {
            this.kg2DbName.put(kgDbName.getKgName(), kgDbName.getDbName());
        }
        cacheAttr = new ConcurrentHashMap<>();
    }

    public KgDbName find(String kgName) {
        return kgAttrDefineMongoTemplate.findOne(query(where("kg_name").is(kgName)), KgDbName.class);
    }

    public List<KgDbName> findAll() {
        return kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
    }


    public List<AttributeDefinition> findAttributeDefinitionList(String kgName, Long attrId) {
        String collectionName = this.kg2DbName.get(kgName) + ATTR_SUFFIX;
        return kgAttrDefineMongoTemplate.find(Query.query(where("domain_value").is(attrId)), AttributeDefinition.class, collectionName);
    }

    public List<AttributeDefinition> getAttr(String kgName, Long attrId) {
        Map<Long, List<AttributeDefinition>> kg = cacheAttr.computeIfAbsent(kgName, (k) -> new HashMap<>());
        List<AttributeDefinition> list = kg.computeIfAbsent(attrId, (k) -> findAttributeDefinitionList(kgName, attrId));
        return list;
    }

    private MongoTemplate template(String kgName) {
        String dbName = kg2DbName.get(kgName);
        return mongoTemplateUtils.template(dbName);
    }

    public List<BasicInfo> getBaseInfoList(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("type").is(0)), BasicInfo.class);
    }

    public List<AttributeString> getAttributeStringList(String kgName, Long entityType) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(Query.query(where("entity_type").is(entityType)), AttributeString.class);
    }

    public List<ParentSon> getParenSonList(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), ParentSon.class);
    }

    public List<AttributeObject> getAttributeObject(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), AttributeObject.class);
    }

    public List<ConceptInstance> getConceptInstance(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), ConceptInstance.class);
    }

    public List<AttributeSummary> getAttributeSummary(String kgName) {
        MongoTemplate mongoTemplate = template(kgName);
        return mongoTemplate.find(new Query(), AttributeSummary.class);
    }

}
