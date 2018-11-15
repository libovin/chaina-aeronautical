package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.ServiceException;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.InstitutionRepository;
import com.hiekn.china.aeronautical.service.InstitutionService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.JsonUtils;
import com.hiekn.china.aeronautical.util.PoiUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.mongodb.WriteResult;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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

    public Map<String, Object> importData(FormDataContentDisposition fileInfo, InputStream fileIn, FormDataBodyPart formDataBodyPart) {
        MediaType type = formDataBodyPart.getMediaType();
        String name = null;
        try {
            name = new String(fileInfo.getFileName().getBytes("iso8859-1"),"utf-8");
        }catch (Exception e) {

        }
        if (name == null) {
            throw ServiceException.newInstance(ErrorCodes.UPLAD_FILE_ERROR);
        }
        List<Map<String, Object>> dataList = null;
        List<Map<String, Object>> errorList = new ArrayList<>();

        if (name.endsWith(".csv")) {
//            dataList = CSVUtils.importCsv(fileIn);
        } else if (name.endsWith(".xls")) {
            dataList = PoiUtils.importXls(fileIn, name);
        } else if (name.endsWith(".json")) {
            dataList = JsonUtils.importJson(fileInfo, fileIn, formDataBodyPart);
        } else {
            throw ServiceException.newInstance(ErrorCodes.UPLAD_FILE_ERROR);
        }
        Map<String, Object> map=new HashMap<>();
        map.put("data",dataList);
        return map;
    }

    public List<Map<String, Object>> checkStat(String key){

        return null;
    }
}
