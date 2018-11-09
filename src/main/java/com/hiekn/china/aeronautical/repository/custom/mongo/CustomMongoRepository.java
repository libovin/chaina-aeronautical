package com.hiekn.china.aeronautical.repository.custom.mongo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CustomMongoRepository<T, ID extends Serializable>{

    <S extends T> S save(S entity,String collectionName);

    <S extends T> List<S> save(Iterable<S> entites,String collectionName);

    <S extends T> S insert(S entity,String collectionName);

    <S extends T> List<S> insert(Iterable<S> entities,String collectionName);


    List<T> findAll(String collectionName);

    List<T> findAll(Sort sort,String collectionName);

    Iterable<T> findAll(Iterable<ID> ids,String collectionName);

    Page<T> findAll(Pageable pageable,String collectionName);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable,String collectionName);

    <S extends T> Iterable<S> findAll(Example<S> example,String collectionName);

    <S extends T> Iterable<S> findAll(Example<S> example, Sort sort,String collectionName);

    T findOne(ID id,String collectionName);

    <S extends T> S findOne(Example<S> example,String collectionName);


    long count(String collectionName);

    <S extends T> long count(Example<S> example,String collectionName);

    void delete(ID id,String collectionName);

    void delete(T entity,String collectionName);

    void delete(Iterable<? extends T> entities,String collectionName);

    void deleteAll(String collectionName);

    boolean exists(ID id,String collectionName);

    <S extends T> boolean exists(Example<S> example,String collectionName);
}
