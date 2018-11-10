package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Institution;
import com.mongodb.WriteResult;

public interface InstitutionRepositoryCustom extends BaseRepositoryCustom<Institution> {

    Institution findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);
}
