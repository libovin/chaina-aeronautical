package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ConferenceService {
    RestData<Conference> findAll(ConferenceQuery conferenceQuery,String collectionName);

    Conference findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Conference modify(String id, Conference conference,String collectionName);

    Conference add(Conference conference,String collectionName);

    RestData<Conference> wordStatistics(WordStatQuery wordStatQuery, String collectionName);

    Map<String, Object> importData(FileImport fileImport, String collectionName);

    List<Map<String, Object>> checkStat(String key);

    void exportData(String collectionName, OutputStream output);
}
