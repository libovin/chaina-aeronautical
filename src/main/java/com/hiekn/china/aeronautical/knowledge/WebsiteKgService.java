package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsiteKgService {

    private final static String schema = "website";

    @Autowired
    private KgBaseService kgBaseService;

    public Website findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Website.class);
    }
}
