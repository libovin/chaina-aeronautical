package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.vo.DictQuery;
import com.hiekn.china.aeronautical.repository.DictRepository;
import com.hiekn.china.aeronautical.service.DictService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public RestData<Dict> findAll(DictQuery bean) {
        Dict targe = new Dict();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        Example<Dict> example = Example.of((Dict) map.get("bean"));
        return new RestData<>(dictRepository.findAll(example), dictRepository.count(example));
    }

    @Override
    public Dict findOne(String id) {
        return dictRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        dictRepository.delete(id);
    }

    @Override
    public Dict modify(String id, Dict dataset) {
        Dict targe = dictRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(dataset);
        BeanUtils.copyProperties(dataset, targe, stringArr);
        return dictRepository.save(targe);
    }

    @Override
    public Dict add(Dict dataset) {
        return dictRepository.save(dataset);
    }
}
