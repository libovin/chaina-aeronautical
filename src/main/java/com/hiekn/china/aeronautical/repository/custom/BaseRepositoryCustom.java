package com.hiekn.china.aeronautical.repository.custom;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseRepositoryCustom<T> {

    <S extends T> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName);

    <S extends T> S insert(S entity, String collectionName);

    <S extends T> S save(S entity, String collectionName);

    void wordStatistics();
}
