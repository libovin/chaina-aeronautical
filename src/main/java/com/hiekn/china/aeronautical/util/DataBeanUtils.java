package com.hiekn.china.aeronautical.util;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataBeanUtils {
    public static <T> String[] getNullProperty(T entity, String... ignoreProperties) {
        PropertyDescriptor[] origDescriptors = BeanUtils.getPropertyDescriptors(entity.getClass());
        BeanMap beanMap = BeanMap.create(entity);
        Set<String> set = new HashSet<>();
        List<String> ignorePropertyList = null;
        if (ignoreProperties != null) {
            ignorePropertyList = Arrays.asList(ignoreProperties);
        }
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue;
            }
            if (ignorePropertyList != null && ignorePropertyList.contains(name)) {
                continue;
            }
            if (beanMap.get(name) == null) {
                set.add(name);
            }
        }
        return set.toArray(new String[]{});
    }

    public static <T> String[] getNullProperty(T o) {
        return getNullProperty(o, null);
    }
}
