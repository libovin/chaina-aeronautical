package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.ServiceException;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.repository.PeriodicalRepository;
import com.hiekn.china.aeronautical.service.PeriodicalService;
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

    public Map<String, Object> importData(FormDataContentDisposition fileInfo, InputStream fileIn, FormDataBodyPart formDataBodyPart) {
        MediaType type = formDataBodyPart.getMediaType();
        String name = null;
        try {
            name = new String(fileInfo.getFileName().getBytes("iso8859-1"), "utf-8");
        } catch (Exception e) {

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
        Map<String, Object> map = new HashMap<>();
        map.put("data", dataList);
        return map;
    }

    public List<Map<String, Object>> checkStat(String key) {

        return null;
    }
}
