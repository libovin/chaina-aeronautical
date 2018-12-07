package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;
import org.springframework.data.util.CloseableIterator;

public interface WebsiteRepositoryCustom extends BaseRepositoryCustom<Website> {

    Website findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    RestData<Website> wordStatistics(WordStatQuery wordStatQuery, String collectionName);

    CloseableIterator<Website> findAllByStream(String collectionName);
}
