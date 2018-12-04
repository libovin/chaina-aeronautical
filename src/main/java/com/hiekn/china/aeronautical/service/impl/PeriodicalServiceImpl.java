package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.PeriodicalRepository;
import com.hiekn.china.aeronautical.service.PeriodicalService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.ExcelUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.hiekn.china.aeronautical.util.SheetHandler;
import com.mongodb.WriteResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service("periodicalService")
public class PeriodicalServiceImpl implements PeriodicalService {


    @Autowired
    private PeriodicalRepository periodicalRepository;

    public RestData<Periodical> findAll(PeriodicalQuery bean, String collectionName) {
        Pageable pageable;
        Periodical targe = new Periodical();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Periodical> example = Example.of((Periodical) map.get("bean"));
        Page<Periodical> p = periodicalRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Periodical findOne(String id, String collectionName) {
        return periodicalRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return periodicalRepository.delete(id, collectionName);
    }

    public Periodical modify(String id, Periodical periodical, String collectionName) {
        Periodical targe = periodicalRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(periodical);
        BeanUtils.copyProperties(periodical, targe, stringArr);
        return periodicalRepository.save(targe, collectionName);
    }

    public Periodical add(Periodical periodical, String collectionName) {
        return periodicalRepository.insert(periodical, collectionName);
    }

    public RestData<Periodical> wordStatistics(WordStatQuery wordStatQuery, String collectionName) {
        return periodicalRepository.wordStatistics(wordStatQuery, collectionName);
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
                        Periodical conference = new Periodical();
                        BeanMap beanMap = BeanMap.create(conference);
                        beanMap.putAll(map);
                        periodicalRepository.insert(conference, collectionName);
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
