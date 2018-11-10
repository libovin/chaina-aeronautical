package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Website;
import com.mongodb.WriteResult;

public interface WebsiteRepositoryCustom extends BaseRepositoryCustom<Website> {

    Website findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

}
