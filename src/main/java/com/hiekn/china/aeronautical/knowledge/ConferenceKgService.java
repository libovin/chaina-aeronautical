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
}
