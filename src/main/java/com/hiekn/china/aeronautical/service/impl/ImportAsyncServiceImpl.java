package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.china.aeronautical.service.ImportAsyncService;
import com.hiekn.china.aeronautical.util.excel.ExcelUtils;
import com.hiekn.china.aeronautical.util.excel.SheetHandler;
import com.hiekn.china.aeronautical.util.xml.XmlHandler;
import com.hiekn.china.aeronautical.util.xml.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.Map;

@Service("importAsyncService")
public class ImportAsyncServiceImpl implements ImportAsyncService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Async("importAsyncExecutor")
    public void importExcelToDataset(Class cls, File file, String collectionName) {
        try {
            new ExcelUtils(new SheetHandler() {
                @Override
                public void endRow(int rowNum) {
                    if (rowNum > 0) {
                        try {
                            Map<String, Object> map = super.getRow();
                            Object object = cls.newInstance();
                            BeanMap beanMap = BeanMap.create(object);
                            beanMap.putAll(map);
                            mongoTemplate.insert(object, collectionName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).process(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importXmlToDataset(Class cls, File file, String collectionName) {
        try {
            new XmlUtils(cls.getSimpleName(), new XmlHandler() {
                @Override
                public void element(String xml) {
                    StringReader sr = new StringReader(xml);
                    try {
                        JAXBContext context = JAXBContext.newInstance(cls);
                        Unmarshaller unmarshaller = context.createUnmarshaller();
                        Object o = unmarshaller.unmarshal(sr);
                        mongoTemplate.insert(o, collectionName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).process(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
