package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.mongodb.WriteResult;

public interface WebsiteService {
    RestData<Website> findAll(WebsiteQuery websiteQuery, String collectionName);

    Website findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    Website modify(String id, Website website, String collectionName);

    Website add(Website website, String collectionName);

    void wordStatistics(String collectionName);
}
