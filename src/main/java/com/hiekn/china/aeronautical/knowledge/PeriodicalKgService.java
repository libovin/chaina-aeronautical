package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodicalKgService {

    private final static String schema = "periodical";

    @Autowired
    private KgBaseService kgBaseService;

    public RestData<Periodical> page(String kgName, PeriodicalQuery periodicalQuery){
        return new RestData<>(find(kgName,periodicalQuery),kgBaseService.count(kgName,schema));
    }

    public Periodical findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Periodical.class);
    }

    public List<Periodical> find(String kgName, PeriodicalQuery periodicalQuery) {
        int page = periodicalQuery.getPageNo();
        int size = periodicalQuery.getPageSize();
        Pageable pageable = new PageRequest(page, size);
        return kgBaseService.find(kgName, schema, pageable, Periodical.class);
    }

    public List<Periodical> findAll(String kgName) {
        return kgBaseService.findAll(kgName, schema, Periodical.class);
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
