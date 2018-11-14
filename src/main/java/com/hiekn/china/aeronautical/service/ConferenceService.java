package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.mongodb.WriteResult;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ConferenceService {
    RestData<Conference> findAll(ConferenceQuery conferenceQuery,String collectionName);

    Conference findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Conference modify(String id, Conference conference,String collectionName);

    Conference add(Conference conference,String collectionName);

    void wordStatistics(String collectionName);

    Map<String, Object> importData(FormDataContentDisposition fileInfo, InputStream fileIn, FormDataBodyPart formDataBodyPart);

    List<Map<String, Object>> checkStat(String key);
}
