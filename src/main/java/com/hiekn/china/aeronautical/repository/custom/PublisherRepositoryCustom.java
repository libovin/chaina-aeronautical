package com.hiekn.china.aeronautical.repository.custom;

import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.mongodb.WriteResult;

public interface PublisherRepositoryCustom extends BaseRepositoryCustom<Publisher> {

    Publisher findOne(String id, String collectionName);

    WriteResult delete(String id, String collectionName);

}
