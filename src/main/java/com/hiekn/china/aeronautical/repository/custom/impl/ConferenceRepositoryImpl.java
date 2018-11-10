package com.hiekn.china.aeronautical.repository.custom.impl;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.repository.custom.ConferenceRepositoryCustom;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import static org.springframework.data.mongodb.core.query.Criteria.where;


public class ConferenceRepositoryImpl extends BaseRepositoryCustomImpl<Conference> implements ConferenceRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Conference findOne(String id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoTemplate.findById(id, Conference.class, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        Query query = new Query(where("id").is(id));
        return mongoTemplate.remove(query, Conference.class, collectionName);
    }

}
