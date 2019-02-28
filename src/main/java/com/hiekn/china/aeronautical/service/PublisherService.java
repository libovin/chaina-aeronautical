package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PublisherQuery;
import com.hiekn.china.aeronautical.model.vo.WordMarkError;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

import java.io.OutputStream;
import java.util.Map;

public interface PublisherService {
    RestData<Publisher> findAll(PublisherQuery publisherQuery, String collectionName);

    Publisher findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    Publisher modify(String id, Publisher publisher, String collectionName);

    Publisher add(Publisher publisher, String collectionName);

    RestData<Publisher> wordStatistics(WordStatQuery wordStatQuery,String collectionName);

    Integer wordMarkError(WordMarkError wordMarkError, String collectionName);

    Map<String, Object> importData(FileImport fileImport, String collectionName);

    void exportData(String collectionName, OutputStream output);
}
