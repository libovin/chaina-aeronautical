package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

public interface PublisherRepositoryCustom extends BaseRepositoryCustom<Publisher> {

    Publisher findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    RestData<Publisher> wordStatistics(WordStatQuery wordStatQuery, String collectionName);

}
