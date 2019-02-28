package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.KgDbInfo;
import com.hiekn.china.aeronautical.repository.KgDbInfoRepository;
import com.hiekn.china.aeronautical.service.KgDbInfoService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("kgDbInfoService")
public class KgDbInfoServiceImpl implements KgDbInfoService {

    @Autowired
    private KgDbInfoRepository kgDbInfoRepository;

    @Override
    public RestData<KgDbInfo> findAll() {
        return new RestData<>(kgDbInfoRepository.findAll(), kgDbInfoRepository.count());
    }

    @Override
    public KgDbInfo findOne(String id) {
        return kgDbInfoRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        kgDbInfoRepository.delete(id);
    }

    @Override
    public KgDbInfo modify(String id, KgDbInfo kgDbInfo) {
        KgDbInfo one = kgDbInfoRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(kgDbInfo);
        BeanUtils.copyProperties(kgDbInfo, one, stringArr);
        return kgDbInfoRepository.save(one);
    }

    @Override
    public KgDbInfo add(KgDbInfo dataset) {
        return kgDbInfoRepository.insert(dataset);
    }
}
