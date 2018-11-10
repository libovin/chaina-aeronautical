package com.hiekn.china.aeronautical.repository.custom.impl;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.repository.custom.ConferenceRepositoryCustom;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;


public class ConferenceRepositoryImpl implements ConferenceRepositoryCustom {

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

    public <S extends Conference> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName) {
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

    public <S extends Conference> S insert(S entity, String collectionName) {
        Assert.notNull(entity, "Entity must not be null!");
        mongoTemplate.insert(entity, collectionName);
        return entity;
    }

    public <S extends Conference> S save(S entity, String collectionName) {
        Assert.notNull(entity, "Entity must not be null!");
        mongoTemplate.save(entity, collectionName);
        return entity;
    }

    public void wordStatistics() {
        String collectionName = "conference";
        String mapFunction = "function() {emit(this.title,1)}";
        String reduceFunction = "function(key,values){return Array.sum(values)}";
        MapReduceResults results = mongoTemplate.mapReduce(collectionName, mapFunction, reduceFunction, Map.class);
        results.forEach(x -> {
            System.out.println(x);
        });
    }

}
