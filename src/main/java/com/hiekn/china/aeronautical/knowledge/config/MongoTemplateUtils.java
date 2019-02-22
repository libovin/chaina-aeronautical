package com.hiekn.china.aeronautical.knowledge.config;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MongoTemplateUtils {

    private Map<String, MongoTemplate> map = new ConcurrentHashMap<>();

    private MongoClient kgMongoClient;

    public MongoTemplateUtils(MongoClient kgMongoClient) {
        this.kgMongoClient = kgMongoClient;
    }

    public MongoTemplate template(String databaseName) {
        return map.computeIfAbsent(databaseName, (k) -> new MongoTemplate(kgMongoClient, databaseName));
    }
}
