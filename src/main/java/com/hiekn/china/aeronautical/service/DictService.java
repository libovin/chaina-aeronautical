package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.vo.DictFileImport;
import com.hiekn.china.aeronautical.model.vo.DictQuery;

public interface DictService {

    RestData<Dict> findAll(DictQuery bean);

    Dict findOne(String id);

    void delete(String id);

    Dict modify(String id, Dict dataset);

    Dict add(Dict dataset);

    Dict importDict(DictFileImport fileImport);
}
