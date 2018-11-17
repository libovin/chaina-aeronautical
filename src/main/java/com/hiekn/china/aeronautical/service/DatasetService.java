package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.vo.DatasetFile;
import com.hiekn.china.aeronautical.model.vo.DatasetQuery;

public interface DatasetService {

    RestData<Dataset> findAll(DatasetQuery bean);

    Dataset findOne(String id);

    void delete(String id);

    Dataset modify(String id, Dataset dataset);

    Dataset add(DatasetFile dataset);
}
