package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.knowledge.bean.AttributeDefinition;
import com.hiekn.china.aeronautical.knowledge.bean.KgDbName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class BaseKnowledgeService {

    @Qualifier("kgAttrDefineMongoTemplate")
    @Autowired
    private MongoTemplate kgAttrDefineMongoTemplate;

    private Map<String,String> kgName ;

    @PostConstruct
    void init(){
        this.kgName = new HashMap<>();
        List<KgDbName> list = kgAttrDefineMongoTemplate.find(new Query(), KgDbName.class);
        for (KgDbName kgDbName :list){
            this.kgName.put(kgDbName.getKgName(),kgDbName.getDbName());
        }
    }



    public KgDbName getKgDbName(String kgName) {
        return kgAttrDefineMongoTemplate.findOne(Query.query(where("kg_name").is(kgName)), KgDbName.class);
    }

    public List<AttributeDefinition> getAttributeDefinitionList(String kgName) {
        String collectionName = this.kgName.get(kgName) + "_attribute_definition" ;
        return kgAttrDefineMongoTemplate.find(new Query(), AttributeDefinition.class,collectionName);
    }
}
