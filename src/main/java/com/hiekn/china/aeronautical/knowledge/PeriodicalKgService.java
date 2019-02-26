package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Periodical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodicalKgService {

    private final static String schema = "periodical";

    @Autowired
    private KgBaseService kgBaseService;

    public Periodical findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Periodical.class);
    }
}
