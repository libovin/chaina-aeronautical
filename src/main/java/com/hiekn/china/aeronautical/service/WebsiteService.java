package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WebsiteService {
    RestData<Website> findAll(WebsiteQuery websiteQuery, String collectionName);

    Website findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    Website modify(String id, Website website, String collectionName);

    Website add(Website website, String collectionName);

    RestData<Website> wordStatistics(WordStatQuery wordStatQuery,String collectionName);

    Map<String, Object> importData(FileImport fileImport,String collectionName);

    List<Map<String, Object>> checkStat(String key);

    void exportData(String type, OutputStream output);
}
