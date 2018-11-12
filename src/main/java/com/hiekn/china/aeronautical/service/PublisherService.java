package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.PublisherQuery;
import com.mongodb.WriteResult;

public interface PublisherService {
    RestData<Publisher> findAll(PublisherQuery publisherQuery, String collectionName);

    Publisher findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    Publisher modify(String id, Publisher publisher, String collectionName);

    Publisher add(Publisher publisher, String collectionName);

    void wordStatistics(String collectionName);
}
