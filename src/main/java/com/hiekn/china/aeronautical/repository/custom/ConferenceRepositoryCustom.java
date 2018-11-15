package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

public interface ConferenceRepositoryCustom extends BaseRepositoryCustom<Conference> {

    Conference findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    RestData<Conference> wordStatistics(WordStatQuery wordStatQuery, String collectionName);
}
