package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.vo.DictFileImport;
import com.hiekn.china.aeronautical.model.vo.DictQuery;
import com.hiekn.china.aeronautical.repository.DictRepository;
import com.hiekn.china.aeronautical.service.DictService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
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
    public Dict modify(String id, Dict dict) {
        Dict targe = dictRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(dict);
        BeanUtils.copyProperties(dict, targe, stringArr);
        return dictRepository.save(targe);
    }

    @Override
    public Dict add(Dict dict) {
        return dictRepository.save(dict);
    }

    @Override
    public Dict importDict(DictFileImport fileImport) {
        Dict dict = new Dict();
        dict.setTable(fileImport.getTable());
        dict.setColumn(fileImport.getColumn());
        dict.setKey(fileImport.getKey());
        dict.setName(fileImport.getName());
        InputStreamReader isr = new InputStreamReader(fileImport.getFileIn());
        LinkedHashSet<String> set = new LinkedHashSet<>();
        try (BufferedReader bufferedReader = new BufferedReader(isr)) {
            String line = null;
            while((line = bufferedReader.readLine())!=null){
                set.add(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        dict.setText(set);
        return dictRepository.save(dict);
    }


}
