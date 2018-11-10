package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.repository.ConferenceRepository;
import com.hiekn.china.aeronautical.service.ConferenceService;
import com.mongodb.WriteResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    public RestData<Conference> findAll(ConferenceQuery bean,String collectionName) {
        Pageable pageable;
        Conference targe = new Conference();
        HashMap map = new HashMap();
        List<Sort.Order> orders = new ArrayList<>();
        //ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null) {
                    Object val = beanMap.get(key);
                    if (val instanceof Map) {
                        Map tempMap = (Map) val;
                        map.put(key, tempMap.get("value"));
                        orders = keyHasSort((String) key, tempMap, orders);
                        //keyHasMatch((String) key, tempMap, matcher);
                    } else {
                        map.put(key, val);
                    }
                }
            }
        }
        Conference conference = mapToBean(map, targe);
        if(orders.size() >0) {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize(), new Sort(orders));
        }else {
            pageable = new PageRequest(bean.getPageNo() - 1, bean.getPageSize());
        }
        Example<Conference> example = Example.of(conference);
        Page<Conference> p = conferenceRepository.findAll(example, pageable, collectionName);
        return new RestData<>(p.getContent(), p.getTotalElements());
    }

    public Conference findOne(String id,String collectionName) {
        return conferenceRepository.findOne(id, collectionName);
    }

    public WriteResult delete(String id, String collectionName) {
        return conferenceRepository.delete(id,collectionName);
    }

    public Conference modify(String id, Conference conference,String collectionName) {
        Conference targe = conferenceRepository.findOne(id,collectionName);
        BeanUtils.copyProperties(conference, targe);
        return conferenceRepository.save(targe,collectionName);
    }

    public Conference add(Conference conference,String collectionName) {
        return conferenceRepository.insert(conference,collectionName);
    }

    public void wordStatistics() {
        conferenceRepository.wordStatistics();
    }


    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public List<Sort.Order> keyHasSort(String key, Map map, List<Sort.Order> orders) {
        Object order = map.get("sort");
        if (order != null) {
            String o = (String) order;
            if (o.equalsIgnoreCase("asc")) {
                orders.add(new Sort.Order(Sort.Direction.ASC, key));
            } else if (o.equalsIgnoreCase("desc")) {
                orders.add(new Sort.Order(Sort.Direction.DESC, key));
            }
        }
        return orders;
    }


//    private ExampleMatcher keyHasMatch(String key, Map map, ExampleMatcher matcher) {
//        Object match = map.get("match");
//        if (match != null) {
//            String o = (String) match;
//            if (o.equalsIgnoreCase("start")) {
//                matcher.withMatcher(key, ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.STARTING));
//            } else if (o.equalsIgnoreCase("end")){
//                matcher.withMatcher(key, ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.ENDING));
//            } else if (o.equalsIgnoreCase("contains")){
//                matcher.withMatcher(key, ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
//            } else if (o.equalsIgnoreCase("exact")){
//                matcher.withMatcher(key, ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.EXACT));
//            } else if (o.equalsIgnoreCase("regex")){
//                matcher.withMatcher(key, ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.REGEX));
//            }
//        }
//        return matcher;
//    }
}
