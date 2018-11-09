package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;

public interface ConferenceService {
    RestData<Conference> findAll(ConferenceQuery conferenceQuery,String collectionName);

    Conference findOne(String id,String collectionName);

    boolean delete(String id,String collectionName);

    Conference modify(String id, Conference conference,String collectionName);

    Conference add(Conference conference,String collectionName);
}
