package com.hiekn.china.aeronautical.util;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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


    public static List<Map<String, String>> getClassProperty(Class cls) {
        List<Map<String, String>> list = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Map<String, String> map = new HashMap<>(2);
            String name = field.getName();
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                String value = annotation.value();
                if (!annotation.hidden()) {
                    map.put("key", name);
                    map.put("value", value);
                    list.add(map);
                }
            }
        }
        return list;
    }

    public static List<String> getFieldList(Class cls) {
        List<String> list = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            list.add(name);
        }
        return list;
    }

    public static List<String> getFieldValueList(Object o) {
        List<String> list = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().getName().equals(String.class.getName())) {
                try {
                    list.add((String) field.get(o));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                list.add(null);
            }
        }
        return list;
    }
}
