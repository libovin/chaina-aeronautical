package com.hiekn.china.aeronautical.knowledge;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionKgService {

    private final static String schema = "institution";

    @Autowired
    private KgBaseService kgBaseService;

    public RestData<Institution> page(String kgName, InstitutionQuery institutionQuery){
        return new RestData<>(find(kgName,institutionQuery),kgBaseService.count(kgName,schema));
    }

    public Institution findOne(String kgName, Long id) {
        return kgBaseService.findOne(kgName, schema, id, Institution.class);
    }

    public List<Institution> find(String kgName, InstitutionQuery institutionQuery) {
        int page = institutionQuery.getPageNo();
        int size = institutionQuery.getPageSize();
        Pageable pageable = new PageRequest(page, size);
        return kgBaseService.find(kgName, schema, pageable, Institution.class);
    }

    public List<Institution> findAll(String kgName) {
        return kgBaseService.findAll(kgName, schema, Institution.class);
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
