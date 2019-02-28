package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.china.aeronautical.model.bean.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionKgService {

    private final static String schema = "institution";

    @Autowired
    private KgBaseService kgBaseService;

    public Institution findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Institution.class);
    }

    public List<Institution> find(String kgName, Pageable pageable) {
        return kgBaseService.find(kgName, schema, pageable, Institution.class);
    }

    public Long count(String kgName) {
        return kgBaseService.count(kgName, schema);
    }

    public void delete(String kgName, Long id) {
        kgBaseService.delete(kgName, schema, id);
    }

    public Institution insert(String kgName, Institution institution) {
       return kgBaseService.insert(kgName, schema, institution);
    }

    public Institution modify(String kgName, Long id, Institution institution) {
       return kgBaseService.modify(kgName, schema, id, institution);
    }
}
