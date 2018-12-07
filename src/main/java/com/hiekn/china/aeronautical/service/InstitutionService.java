package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.mongodb.WriteResult;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface InstitutionService {
    RestData<Institution> findAll(InstitutionQuery institutionQuery, String collectionName);

    Institution findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Institution modify(String id, Institution institution,String collectionName);

    Institution add(Institution institution,String collectionName);

    RestData<Institution> wordStatistics(WordStatQuery wordStatQuery,String collectionName);

    Map<String, Object> importData(FileImport fileImport, String collectionName);

    List<Map<String, Object>> checkStat(String key);

    void exportData(String collectionName, OutputStream output);
}
