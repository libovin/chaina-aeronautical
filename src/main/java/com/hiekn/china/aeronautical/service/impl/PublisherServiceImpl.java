package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PublisherQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.PublisherRepository;
import com.hiekn.china.aeronautical.service.PublisherService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.ExcelUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.hiekn.china.aeronautical.util.SheetHandler;
import com.mongodb.WriteResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service("publisherService")
public class PublisherServiceImpl implements PublisherService {


    @Autowired
    private PublisherRepository publisherRepository;

    public RestData<Publisher> findAll(PublisherQuery bean, String collectionName) {
        Pageable pageable;
        Publisher targe = new Publisher();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Publisher> example = Example.of((Publisher) map.get("bean"));
        Page<Publisher> p = publisherRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Publisher findOne(String id, String collectionName) {
        return publisherRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return publisherRepository.delete(id, collectionName);
    }

    public Publisher modify(String id, Publisher publisher, String collectionName) {
        Publisher targe = publisherRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(publisher);
        BeanUtils.copyProperties(publisher, targe, stringArr);
        return publisherRepository.save(targe, collectionName);
    }

    public Publisher add(Publisher publisher, String collectionName) {
        return publisherRepository.insert(publisher, collectionName);
    }

    public RestData<Publisher> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        return publisherRepository.wordStatistics(wordStatQuery, collectionName);
    }

    public Map<String, Object> importData(FileImport fileImport, String collectionName) {
        if (fileImport.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                new ExcelUtils(new SheetHandler() {
                    @Override
                    public void endRow(int rowNum) {
                        Map<String, Object> map = super.getRow();
                        Publisher conference = new Publisher();
                        BeanMap beanMap = BeanMap.create(conference);
                        beanMap.putAll(map);
                        publisherRepository.insert(conference, collectionName);
                    }
                }).process(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Map<String, Object>> checkStat(String key) {

        return null;
    }
}
