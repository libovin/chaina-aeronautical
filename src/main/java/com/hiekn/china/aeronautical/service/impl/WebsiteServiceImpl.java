package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.WebsiteRepository;
import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.service.WebsiteService;
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

@Service("websiteService")
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private ImportAsyncService importAsyncService;

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
        Map<String, Object> map = new HashMap<>(2);
        if (fileImport.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                map.put("msg", "上传成功");
                map.put("code", "success");
                importAsyncService.importExcelToDataset(Website.class, file, collectionName);
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

    public void exportData(String type, OutputStream output) {
        try {
            Path path = Paths.get("D:/tinydata.xlsx");
            byte[] data = Files.readAllBytes(path);
            output.write(data);
            output.flush();
        } catch (Exception e) {

        }
    }
}
