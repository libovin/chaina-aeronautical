package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.WebsiteRepository;
import com.hiekn.china.aeronautical.service.WebsiteService;
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

@Service("websiteService")
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    public RestData<Website> findAll(WebsiteQuery bean, String collectionName) {
        Pageable pageable;
        Website targe = new Website();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Website> example = Example.of((Website) map.get("bean"));
        Page<Website> p = websiteRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Website findOne(String id, String collectionName) {
        return websiteRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return websiteRepository.delete(id, collectionName);
    }

    public Website modify(String id, Website website, String collectionName) {
        Website targe = websiteRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(website);
        BeanUtils.copyProperties(website, targe, stringArr);
        return websiteRepository.save(targe, collectionName);
    }

    public Website add(Website website, String collectionName) {
        return websiteRepository.insert(website, collectionName);
    }

    public RestData<Website> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        return websiteRepository.wordStatistics(wordStatQuery, collectionName);
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
                        Website conference = new Website();
                        BeanMap beanMap = BeanMap.create(conference);
                        beanMap.putAll(map);
                        websiteRepository.insert(conference, collectionName);
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
