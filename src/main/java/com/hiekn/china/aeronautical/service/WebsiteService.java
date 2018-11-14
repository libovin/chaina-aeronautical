package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.mongodb.WriteResult;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface WebsiteService {
    RestData<Website> findAll(WebsiteQuery websiteQuery, String collectionName);

    Website findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    Website modify(String id, Website website, String collectionName);

    Website add(Website website, String collectionName);

    void wordStatistics(String collectionName);

    Map<String, Object> importData(FormDataContentDisposition fileInfo, InputStream fileIn, FormDataBodyPart formDataBodyPart);

    List<Map<String, Object>> checkStat(String key);
}
