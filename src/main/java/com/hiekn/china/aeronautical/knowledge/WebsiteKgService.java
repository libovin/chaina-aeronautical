package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteKgService {

    private final static String schema = "website";

    @Autowired
    private KgBaseService kgBaseService;

    public RestData<Website> page(String kgName, WebsiteQuery websiteQuery) {
        return new RestData<>(find(kgName, websiteQuery), kgBaseService.count(kgName, schema));
    }

    public Website findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Website.class);
    }

    public List<Website> find(String kgName, WebsiteQuery websiteQuery) {
        int page = websiteQuery.getPageNo();
        int size = websiteQuery.getPageSize();
        Pageable pageable = new PageRequest(page, size);
        return kgBaseService.find(kgName, schema, pageable, Website.class);
    }

    public List<Website> findAll(String kgName) {
        return kgBaseService.findAll(kgName, schema, Website.class);
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
