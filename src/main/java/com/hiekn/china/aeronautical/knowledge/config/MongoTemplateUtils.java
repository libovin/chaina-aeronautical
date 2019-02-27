package com.hiekn.china.aeronautical.knowledge.config;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MongoTemplateUtils {

    private Map<String, MongoTemplate> map = new ConcurrentHashMap<>();

    private MongoClient kgMongoClient;

    private MappingMongoConverter mappingMongoConverter;

    public MongoTemplateUtils(MongoClient kgMongoClient, MappingMongoConverter mappingMongoConverter) {
        this.mappingMongoConverter = mappingMongoConverter;
        this.kgMongoClient = kgMongoClient;
    }

    public MongoTemplate template(String databaseName) {
        return map.computeIfAbsent(databaseName, (k) -> new MongoTemplate(new SimpleMongoDbFactory(kgMongoClient, databaseName), mappingMongoConverter));
    }
}
