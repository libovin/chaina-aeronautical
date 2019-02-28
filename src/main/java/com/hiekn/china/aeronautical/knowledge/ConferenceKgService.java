package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceKgService {

    private final static String schema = "conference";

    @Autowired
    private KgBaseService kgBaseService;

    public RestData<Conference> page(String kgName, ConferenceQuery conferenceQuery){
        return new RestData<>(find(kgName,conferenceQuery),kgBaseService.count(kgName,schema));
    }

    public Conference findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Conference.class);
    }

    public List<Conference> find(String kgName, ConferenceQuery conferenceQuery) {
        int page = conferenceQuery.getPageNo();
        int size = conferenceQuery.getPageSize();
        Pageable pageable = new PageRequest(page, size);
        return kgBaseService.find(kgName, schema, pageable, Conference.class);
    }

    public List<Conference> findAll(String kgName) {
        return kgBaseService.findAll(kgName, schema, Conference.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }

    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public Conference insert(String kgName, Conference conference) {
        return kgBaseService.insert(kgName, schema, conference);
    }

    public Conference modify(String kgName, Long id, Conference conference) {
        return kgBaseService.modify(kgName, schema, id, conference);
    }

}
