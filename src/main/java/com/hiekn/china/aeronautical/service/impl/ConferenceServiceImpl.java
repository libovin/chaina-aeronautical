package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.WordMarkError;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.ConferenceRepository;
import com.hiekn.china.aeronautical.service.ConferenceService;
import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.ExportUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.mongodb.WriteResult;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ImportAsyncService importAsyncService;

    public RestData<Conference> findAll(ConferenceQuery bean, String collectionName) {
        Pageable pageable;
        Conference targe = new Conference();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Conference> example = Example.of((Conference) map.get("bean"));
        Page<Conference> p = conferenceRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Conference findOne(String id, String collectionName) {
        return conferenceRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return conferenceRepository.delete(id, collectionName);
    }

    public Conference modify(String id, Conference conference, String collectionName) {
        Conference targe = conferenceRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(conference);
        BeanUtils.copyProperties(conference, targe, stringArr);
        return conferenceRepository.save(targe, collectionName);
    }

    public Conference add(Conference conference, String collectionName) {
        return conferenceRepository.insert(conference, collectionName);
    }

    public RestData<Conference> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        return conferenceRepository.wordStatistics(wordStatQuery, collectionName);
    }

    public Integer wordMarkError(WordMarkError wordMarkError, String collectionName) {
        Query query = Query.query(Criteria.where("_id").in(Arrays.asList(wordMarkError.getIds().split(","))));
        Update update =Update.update("hasError",true).set("hasErrorTag."+wordMarkError.getColumn(), true);
        WriteResult writeResult = conferenceRepository.updateMulti(query,update,collectionName);
        return writeResult.getN();
    }

    public Map<String, Object> importData(FileImport fileImport, String collectionName) {
        Map<String, Object> map = new HashMap<>(2);
        if (fileImport.getFileInfo() != null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                map.put("msg", "上传成功");
                map.put("code", "success");
                importAsyncService.importExcelToDataset(Conference.class, file, collectionName);
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

    public void exportData(String collectionName, OutputStream output) {
        try {
            Workbook wb = new SXSSFWorkbook(100);
            CloseableIterator<Conference> c = conferenceRepository.findAllByStream(collectionName);
            int index = 1;
            Sheet sheet = wb.createSheet();
            ExportUtils.addOneRow(sheet.createRow(0), DataBeanUtils.getFieldList(Conference.class));
            while (c.hasNext()) {
                Conference conference = c.next();
                ExportUtils.addOneRow(sheet.createRow(index), DataBeanUtils.getFieldValueList(conference));
                index++;
            }
            wb.write(output);
            c.close();
            output.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
