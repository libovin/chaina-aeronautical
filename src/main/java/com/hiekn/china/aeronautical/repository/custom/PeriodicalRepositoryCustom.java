package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

public interface PeriodicalRepositoryCustom extends BaseRepositoryCustom<Periodical> {

    Periodical findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

    RestData<Periodical> wordStatistics(WordStatQuery wordStatQuery, String collectionName);
}
