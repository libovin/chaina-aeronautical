package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.repository.custom.InstitutionRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstitutionRepository extends MongoRepository<Institution,String>, InstitutionRepositoryCustom {
}
