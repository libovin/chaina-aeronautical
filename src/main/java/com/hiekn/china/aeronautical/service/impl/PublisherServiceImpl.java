package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PublisherQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.PublisherRepository;
import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.service.PublisherService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.mongodb.WriteResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("publisherService")
public class PublisherServiceImpl implements PublisherService {


    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ImportAsyncService importAsyncService;

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
        Map<String, Object> map = new HashMap<>(2);
        if (fileImport.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                map.put("msg", "上传成功");
                map.put("code", "success");
                importAsyncService.importExcelToDataset(Publisher.class, file, collectionName);
            } catch (IOException e) {
                map.put("msg", "上传失败，无法上传文件");
                map.put("code", "fail");
            }
        } else {
            map.put("msg", "上传失败，文件不能为空");
            map.put("code", "fail");
        }
        return map;
    }

    public List<Map<String, Object>> checkStat(String key) {

        return null;
    }

    public void exportData(String type, OutputStream output){
        try {
            Path path = Paths.get("D:/tinydata.xlsx");
            byte[] data = Files.readAllBytes(path);
            output.write(data);
            output.flush();
        } catch (Exception e) {

        }
    }
}
