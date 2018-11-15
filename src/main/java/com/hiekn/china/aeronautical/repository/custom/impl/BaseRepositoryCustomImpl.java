package com.hiekn.china.aeronautical.repository.custom.impl;

import com.hiekn.china.aeronautical.model.vo.Result;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.custom.BaseRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class BaseRepositoryCustomImpl<T> implements BaseRepositoryCustom<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public <S extends T> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        final Query q = new Query(new Criteria().alike(example)).with(pageable);
        List<S> list = mongoTemplate.find(q, example.getProbeType(), collectionName);
        return PageableExecutionUtils.getPage(list, pageable, new PageableExecutionUtils.TotalSupplier() {
            @Override
            public long get() {
                return mongoTemplate.count(q, example.getProbeType(), collectionName);
            }
        });
    }

    public <S extends T> S insert(S entity, String collectionName) {
        Assert.notNull(entity, "Entity must not be null!");
        mongoTemplate.insert(entity, collectionName);
        return entity;
    }

    public <S extends T> S save(S entity, String collectionName) {
        Assert.notNull(entity, "Entity must not be null!");
        mongoTemplate.save(entity, collectionName);
        return entity;
    }


    public List<Result> getAggResult (WordStatQuery wq, String collectionName){
        AggregationResults<Result> agg = mongoTemplate.aggregate(
                newAggregation(
                        group(wq.getColumn())
                                .count().as("count"),
                        match(where("count").gte(wq.getMin()).lte(wq.getMax())),
                        project(wq.getColumn(), "count")
                                .and("name").previousOperation()
                ), collectionName, Result.class);
        return agg.getMappedResults();
    }
}
