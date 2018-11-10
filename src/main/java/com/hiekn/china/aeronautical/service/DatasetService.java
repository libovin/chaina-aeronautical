package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dataset;

public interface DatasetService {

    RestData<Dataset> findAll();

    Dataset findOne(String id);

    void delete(String id);

    Dataset modify(String id, Dataset dataset);

    Dataset add(Dataset dataset);
}
