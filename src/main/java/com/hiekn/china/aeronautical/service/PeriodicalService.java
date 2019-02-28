package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.model.vo.WordMarkError;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

import java.io.OutputStream;
import java.util.Map;

public interface PeriodicalService {
    RestData<Periodical> findAll(PeriodicalQuery periodicalQuery, String collectionName);

    Periodical findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Periodical modify(String id, Periodical periodical,String collectionName);

    Periodical add(Periodical periodical,String collectionName);

    RestData<Periodical> wordStatistics(WordStatQuery wordStatQuery,String collectionName);

    Integer wordMarkError(WordMarkError wordMarkError, String collectionName);

    Map<String, Object> importData(FileImport fileImport, String collectionName);

    void exportData(String collectionName, OutputStream output);
}
