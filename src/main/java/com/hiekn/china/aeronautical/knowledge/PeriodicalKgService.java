package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Periodical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodicalKgService {

    private final static String schema = "periodical";

    @Autowired
    private KgBaseService kgBaseService;

    public Periodical findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Periodical.class);
    }

    public List<Periodical> find(String kgName, Pageable pageable) {
        return kgBaseService.find(kgName, schema, pageable, Periodical.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }

    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public Periodical insert(String kgName, Periodical periodical) {
       return kgBaseService.insert(kgName, schema, periodical);
    }

    public Periodical modify(String kgName, Long id, Periodical periodical) {
       return kgBaseService.modify(kgName, schema, id, periodical);
    }
}
