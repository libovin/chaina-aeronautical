package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.mongodb.WriteResult;

public interface InstitutionService {
    RestData<Institution> findAll(InstitutionQuery institutionQuery, String collectionName);

    Institution findOne(String id,String collectionName);

    WriteResult delete(String id, String collectionName);

    Institution modify(String id, Institution institution,String collectionName);

    Institution add(Institution institution,String collectionName);

    void wordStatistics(String collectionName);
}
