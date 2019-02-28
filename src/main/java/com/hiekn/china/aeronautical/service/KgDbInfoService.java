package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.KgDbInfo;

public interface KgDbInfoService {

    RestData<KgDbInfo> findAll();

    KgDbInfo findOne(String id);

    void delete(String id);

    KgDbInfo modify(String id, KgDbInfo dataset);

    KgDbInfo add(KgDbInfo dataset);

}
