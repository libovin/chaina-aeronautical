package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.repository.ConferenceRepository;
import com.hiekn.china.aeronautical.service.ConferenceService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import com.mongodb.WriteResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

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

    public void wordStatistics(String collectionName) {
        conferenceRepository.wordStatistics(collectionName);
    }
}
