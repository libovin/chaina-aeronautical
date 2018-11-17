package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.InstitutionRepository;
import com.hiekn.china.aeronautical.service.InstitutionService;
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

@Service("institutionService")
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public RestData<Institution> findAll(InstitutionQuery bean, String collectionName) {
        Pageable pageable;
        Institution targe = new Institution();
        Map<String,Object> map = QueryUtils.trastation(bean, targe);
        List<Sort.Order> orders = (List<Sort.Order>) map.get("sort");
        if (orders.size() > 0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        } else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Institution> example = Example.of((Institution) map.get("bean"));
        Page<Institution> p = institutionRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Institution findOne(String id, String collectionName) {
        return institutionRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return institutionRepository.delete(id, collectionName);
    }

    public Institution modify(String id, Institution institution, String collectionName) {
        Institution targe = institutionRepository.findOne(id, collectionName);
        String[] stringArr = DataBeanUtils.getNullProperty(institution);
        BeanUtils.copyProperties(institution, targe, stringArr);
        return institutionRepository.save(targe, collectionName);
    }

    public Institution add(Institution institution, String collectionName) {
        return institutionRepository.insert(institution, collectionName);
    }

    public RestData<Institution> wordStatistics(WordStatQuery wordStatQuery,String collectionName) {
       return institutionRepository.wordStatistics(wordStatQuery,collectionName);
    }

    public Map<String, Object> importData(FileImport fileImport, String collectionName) {
        if(fileImport.getFileInfo()!= null) {
            File file = new File("temp" + System.currentTimeMillis());
            try {
                FileUtils.copyInputStreamToFile(fileImport.getFileIn(), file);
                new ExcelUtils(new SheetHandler() {
                    @Override
                    public void endRow(int rowNum) {
                        Map<String, Object> map = super.getRow();
                        Institution conference = new Institution();
                        BeanMap beanMap = BeanMap.create(conference);
                        beanMap.putAll(map);
                        institutionRepository.insert(conference, collectionName);
                    }
                }).process(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Map<String, Object>> checkStat(String key){

        return null;
    }
}
