package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.repository.DatasetRepository;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("datasetService")
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    public RestData<Dataset> findAll() {
        List<Dataset> list = datasetRepository.findAll();
        Long count = datasetRepository.count();
        return new RestData<>(list, count);
    }

    @Override
    public Dataset findOne(String id) {
        return datasetRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        datasetRepository.delete(id);
    }

    @Override
    public Dataset modify(String id, Dataset dataset) {
        Dataset targe = datasetRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(dataset);
        BeanUtils.copyProperties(dataset, targe, stringArr);
        return datasetRepository.save(targe);
    }

    @Override
    public Dataset add(Dataset dataset) {
        return datasetRepository.save(dataset);
    }


}
