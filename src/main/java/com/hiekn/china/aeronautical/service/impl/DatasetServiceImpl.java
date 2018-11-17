package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.vo.DatasetFile;
import com.hiekn.china.aeronautical.model.vo.DatasetQuery;
import com.hiekn.china.aeronautical.repository.ConferenceRepository;
import com.hiekn.china.aeronautical.repository.DatasetRepository;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.ExcelUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.hiekn.china.aeronautical.util.SheetHandler;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service("datasetService")
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public RestData<Dataset> findAll(DatasetQuery bean) {
        Dataset targe = new Dataset();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        Example<Dataset> example = Example.of((Dataset) map.get("bean"));
        return new RestData<>(datasetRepository.findAll(example), datasetRepository.count(example));
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
    public Dataset add(DatasetFile datesetFile){
        Dataset dataset = new Dataset();
        dataset.setTable(datesetFile.getTable());
        dataset.setKey(datesetFile.getKey());
        dataset.setTypeKey(datesetFile.getTable() +"_"+ datesetFile.getKey());
        dataset.setName(datesetFile.getName());
        if (datasetRepository.existsDatasetByTypeKey(dataset.getTypeKey())){
            throw RestException.newInstance(ErrorCodes.NAME_EXIST_ERROR);
        }
        Dataset dataset1 = datasetRepository.save(dataset);
        if(datesetFile.getFileInfo()!= null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(datesetFile.getFileIn(), file);
                new ExcelUtils(new SheetHandler() {
                    @Override
                    public void endRow(int rowNum) {
                        Map<String, Object> map = super.getRow();
                        Conference conference = new Conference();
                        BeanMap beanMap = BeanMap.create(conference);
                        beanMap.putAll(map);
                        conferenceRepository.insert(conference, dataset.getTypeKey());
                    }
                }).process(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataset1;
    }



}
