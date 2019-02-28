package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteKgService {

    private final static String schema = "website";

    @Autowired
    private KgBaseService kgBaseService;

    public Website findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Website.class);
    }

    public List<Website> find(String kgName, Pageable pageable) {
        return kgBaseService.find(kgName, schema, pageable, Website.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }

    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public Website insert(String kgName, Website website) {
        return kgBaseService.insert(kgName, schema, website);
    }

    public Website modify(String kgName, Long id, Website website) {
        return kgBaseService.modify(kgName, schema, id, website);
    }
}
