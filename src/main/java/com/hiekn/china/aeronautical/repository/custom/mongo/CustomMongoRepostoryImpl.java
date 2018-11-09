package com.hiekn.china.aeronautical.repository.custom.mongo;

import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CustomMongoRepostoryImpl<T, ID extends Serializable> implements CustomMongoRepository<T, ID> {

    private final MongoOperations mongoOperations;
    private final MongoWithoutCollectionEntityInformation<T, ID> entityInformation;

    public CustomMongoRepostoryImpl(MongoWithoutCollectionEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        Assert.notNull(mongoOperations, "MongoOperations must not be null!");
        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;
    }

    public <S extends T> S save(S entity, String collectionName) {
        Assert.notNull(entity, "Entity must not be null!");
        if (entityInformation.isNew(entity)) {
            mongoOperations.insert(entity, collectionName);
        } else {
            mongoOperations.save(entity, collectionName);
        }
        return entity;
    }

    public <S extends T> List<S> save(Iterable<S> entities, String collectionName) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        List<S> result = convertIterableToList(entities);
        boolean allNew = true;
        for (S entity : entities) {
            if (allNew && !entityInformation.isNew(entity)) {
                allNew = false;
            }
        }
        if (allNew) {
            mongoOperations.insert(result, collectionName);
        } else {
            for (S entity : result) {
                save(entity, collectionName);
            }
        }
        return result;
    }

    public T findOne(ID id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoOperations.findById(id, entityInformation.getJavaType(), collectionName);
    }

    private Query getIdQuery(Object id) {
        return new Query(getIdCriteria(id));
    }

    private Criteria getIdCriteria(Object id) {
        return where(entityInformation.getIdAttribute()).is(id);
    }

    public boolean exists(ID id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoOperations.exists(getIdQuery(id), entityInformation.getJavaType(),
                collectionName);
    }

    public long count(String collectionName) {
        return mongoOperations.getCollection(collectionName).count();
    }

    public void delete(ID id, String collectionName) {
        Assert.notNull(id, "The given id must not be null!");
        mongoOperations.remove(getIdQuery(id), entityInformation.getJavaType(), collectionName);
    }

    public void delete(T entity, String collectionName) {
        Assert.notNull(entity, "The given entity must not be null!");
        delete(entityInformation.getId(entity), collectionName);
    }

    public void delete(Iterable<? extends T> entities, String collectionName) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        for (T entity : entities) {
            delete(entity, collectionName);
        }
    }

    public void deleteAll(String collectionName) {
        mongoOperations.remove(new Query(), collectionName);
    }

    public List<T> findAll(String collectionName) {
        return findAll(new Query(), collectionName);
    }

    public Iterable<T> findAll(Iterable<ID> ids, String collectionName) {
        Set<ID> parameters = new HashSet<ID>(tryDetermineRealSizeOrReturn(ids, 10));
        for (ID id : ids) {
            parameters.add(id);
        }
        return findAll(new Query(new Criteria(entityInformation.getIdAttribute()).in(parameters)), collectionName);
    }

    public Page<T> findAll(final Pageable pageable, String collectionName) {
        Long count = count(collectionName);
        List<T> list = findAll(new Query().with(pageable), collectionName);
        return new PageImpl<T>(list, pageable, count);
    }

    public List<T> findAll(Sort sort, String collectionName) {
        return findAll(new Query().with(sort), collectionName);
    }

    @Override
    public <S extends T> S insert(S entity, String collectionName) {

        Assert.notNull(entity, "Entity must not be null!");

        mongoOperations.insert(entity, collectionName);
        return entity;
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities, String collectionName) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        return save(entities, collectionName);
    }

    @Override
    public <S extends T> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        final Query q = new Query(new Criteria().alike(example)).with(pageable);
        List<S> list = mongoOperations.find(q, example.getProbeType(), collectionName);
        return PageableExecutionUtils.getPage(list, pageable, new PageableExecutionUtils.TotalSupplier() {
            @Override
            public long get() {
                return mongoOperations.count(q, example.getProbeType(), collectionName);
            }
        });
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        Query q = new Query(new Criteria().alike(example));
        if (sort != null) {
            q.with(sort);
        }
        return mongoOperations.find(q, example.getProbeType(), collectionName);
    }


    @Override
    public <S extends T> List<S> findAll(Example<S> example, String collectionName) {
        return findAll(example, (Sort) null, collectionName);
    }

    @Override
    public <S extends T> S findOne(Example<S> example, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        Query q = new Query(new Criteria().alike(example));
        return mongoOperations.findOne(q, example.getProbeType(), collectionName);
    }

    @Override
    public <S extends T> long count(Example<S> example, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        Query q = new Query(new Criteria().alike(example));
        return mongoOperations.count(q, example.getProbeType(), collectionName);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example, String collectionName) {
        Assert.notNull(example, "Sample must not be null!");
        Query q = new Query(new Criteria().alike(example));
        return mongoOperations.exists(q, example.getProbeType(), collectionName);
    }

    private List<T> findAll(Query query, String collectionName) {
        if (query == null) {
            return Collections.emptyList();
        }
        return mongoOperations.find(query, entityInformation.getJavaType(), collectionName);
    }

    private static <T> List<T> convertIterableToList(Iterable<T> entities) {
        if (entities instanceof List) {
            return (List<T>) entities;
        }
        int capacity = tryDetermineRealSizeOrReturn(entities, 10);
        if (capacity == 0 || entities == null) {
            return Collections.<T>emptyList();
        }
        List<T> list = new ArrayList<T>(capacity);
        for (T entity : entities) {
            list.add(entity);
        }
        return list;
    }

    private static int tryDetermineRealSizeOrReturn(Iterable<?> iterable, int defaultSize) {
        return iterable == null ? 0 : (iterable instanceof Collection) ? ((Collection<?>) iterable).size() : defaultSize;
    }
}
