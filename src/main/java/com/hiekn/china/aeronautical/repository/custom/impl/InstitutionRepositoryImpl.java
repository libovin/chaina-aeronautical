package com.hiekn.china.aeronautical.repository.custom.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.Result;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.custom.InstitutionRepositoryCustom;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    public RestData<Institution> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        List<Result> results = getAggResult(wordStatQuery, collectionName);
        Set<String> key =new HashSet<>();
        BigDecimal a = BigDecimal.ZERO;
        for (Result r :results) {
            key.add(r.getName());
            a = a.add(r.getCount());
        }
        Pageable pageable = new PageRequest(wordStatQuery.getPageNo() - 1, wordStatQuery.getPageSize());
        List<Institution> list =  mongoTemplate.find(query(where(wordStatQuery.getColumn()).in(key)).with(pageable), Institution.class, collectionName);
        return new RestData<>(list,a.intValue());
    }

    public CloseableIterator<Institution> findAllByStream(String collectionName) {
        Query query = new Query();
        return mongoTemplate.stream(query, Institution.class, collectionName);
    }
}
