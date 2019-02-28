package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceKgService {

    private final static String schema = "conference";

    @Autowired
    private KgBaseService kgBaseService;

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

    public Conference insert(String kgName, Conference conference) {
        return kgBaseService.insert(kgName, schema, conference);
    }

    public Conference modify(String kgName, Long id, Conference conference) {
        return kgBaseService.modify(kgName, schema, id, conference);
    }


//    public CloseableIterator<Conference> findAllByStream(String kgName) {
//        return kgBaseService.stream(kgName,schema);
//    }

}
