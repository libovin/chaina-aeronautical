package com.hiekn.china.aeronautical.repository.custom.impl;

import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.repository.custom.InstitutionRepositoryCustom;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class InstitutionRepositoryImpl extends BaseRepositoryCustomImpl<Institution> implements InstitutionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Institution findOne(String id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoTemplate.findById(id, Institution.class, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        Query query = new Query(where("id").is(id));
        return mongoTemplate.remove(query, Institution.class, collectionName);
    }

}
