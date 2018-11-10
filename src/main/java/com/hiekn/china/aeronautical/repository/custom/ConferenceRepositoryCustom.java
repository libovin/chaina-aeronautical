package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.mongodb.WriteResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConferenceRepositoryCustom {
    void wordStatistics();
    Conference findOne(String id, String collectionName);
    WriteResult delete(String id, String collectionName);
    <S extends Conference> Page<S> findAll(final Example<S> example, Pageable pageable, String collectionName);
    <S extends Conference> S insert(S entity, String collectionName);
    <S extends Conference> S save(S entity, String collectionName);
}
