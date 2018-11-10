package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Institution;
import com.mongodb.WriteResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InstitutionRepositoryCustom {
    void wordStatistics();
    Institution findOne(String id, String collectionName);
    WriteResult delete(String id, String collectionName);
    <S extends Institution> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName);
    <S extends Institution> S insert(S entity, String collectionName);
    <S extends Institution> S save(S entity, String collectionName);
}
