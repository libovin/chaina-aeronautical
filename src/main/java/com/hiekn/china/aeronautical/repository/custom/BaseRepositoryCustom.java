package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.vo.Result;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface BaseRepositoryCustom<T> {

    <S extends T> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName);

    <S extends T> S insert(S entity, String collectionName);

    <S extends T> S save(S entity, String collectionName);

    List<Result> getAggResult (WordStatQuery wq, String collectionName);

    WriteResult updateMulti (Query query, Update update, String collectionName);
}
