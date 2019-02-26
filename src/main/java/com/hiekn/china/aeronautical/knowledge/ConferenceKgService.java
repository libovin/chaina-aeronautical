package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceKgService {

    @Autowired
    private KgBaseService kgBaseService;

    private final static String schema = "conference";

    public Conference findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Conference.class);
    }

    public List<Conference> find(String kgName, Pageable pageable) {
        return kgBaseService.find(kgName, schema, pageable, Conference.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }


    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public void insert(String kgName, Conference conference) {
        kgBaseService.insert(kgName,schema,conference);
    }

    public void modify(String kgName, Conference conference) {

    }

}
