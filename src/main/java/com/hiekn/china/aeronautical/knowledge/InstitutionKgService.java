package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionKgService {

    private final static String schema = "institution";

    @Autowired
    private KgBaseService kgBaseService;


    public Institution findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Institution.class);
    }
}
