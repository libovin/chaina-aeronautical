package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Website;
import com.mongodb.WriteResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebsiteRepositoryCustom {
    void wordStatistics();
    Website findOne(String id, String collectionName);
    WriteResult delete(String id, String collectionName);
    <S extends Website> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName);
    <S extends Website> S insert(S entity, String collectionName);
    <S extends Website> S save(S entity, String collectionName);
}
