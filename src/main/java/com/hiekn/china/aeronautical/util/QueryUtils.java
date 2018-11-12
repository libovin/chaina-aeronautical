package com.hiekn.china.aeronautical.util;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtils {
    public static <T> Map<String, Object> trastation(Object bean, T targe) {
        Map<String, Object> hashMap = new HashMap<>();
        //ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        if (bean != null) {
            List<Sort.Order> orders = new ArrayList<>();
            HashMap map = new HashMap();
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null) {
                    Object val = beanMap.get(key);
                    if (val instanceof Map) {
                        Map tempMap = (Map) val;
                        map.put(key, tempMap.get("value"));
                        orders = keyHasSort((String) key, tempMap, orders);
                        //keyHasMatch((String) key, tempMap, matcher);
                    } else if (val instanceof List) {

                    } else {
                        map.put(key, val);
                    }
                }
            }
            hashMap.put("bean",mapToBean(map, targe));
            hashMap.put("sort",orders);
        }
        return hashMap;
    }

    private static List<Sort.Order> keyHasSort(String key, Map map, List<Sort.Order> orders) {
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

    private static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
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
