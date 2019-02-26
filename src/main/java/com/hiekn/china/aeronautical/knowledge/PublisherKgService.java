package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherKgService {

    private final static String schema = "publisher";

    @Autowired
    private KgBaseService kgBaseService;

    public Publisher findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Publisher.class);
    }
}
