package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.mongodb.WriteResult;

public interface PeriodicalService {
    RestData<Periodical> findAll(PeriodicalQuery periodicalQuery, String collectionName);

    Periodical findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Periodical modify(String id, Periodical periodical,String collectionName);

    Periodical add(Periodical periodical,String collectionName);

    void wordStatistics(String collectionName);
}
