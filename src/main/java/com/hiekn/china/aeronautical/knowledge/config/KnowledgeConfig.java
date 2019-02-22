package com.hiekn.china.aeronautical.knowledge.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class KnowledgeConfig {

    private Integer port;

    private String host;

    private static String KG_ATTRIBUTE_DEFINITION = "kg_attribute_definition";

    @Bean(name = "kgMongoClient")
    public MongoClient kgMongoClient() {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(10)
                .socketTimeout(30000)
                .connectTimeout(3000)
                .build();
        return new MongoClient(serverAddress, options);
    }

    @Bean
    public MongoTemplateUtils mongoTemplateUtils(MongoClient kgMongoClient) {
        return new MongoTemplateUtils(kgMongoClient);
    }

    @Primary
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MappingMongoConverter mappingMongoConverter) {
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }

    @Qualifier
    @Bean(name = "kgAttrDefineMongoTemplate")
    public MongoTemplate kgAttrDefineMongoTemplate(MongoTemplateUtils mongoTemplateUtils) {
        return mongoTemplateUtils.template(KG_ATTRIBUTE_DEFINITION);
    }
}
