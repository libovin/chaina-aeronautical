package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.repository.custom.mongo.CustomMongoRepository;

public interface InstitutionRepository extends CustomMongoRepository<Conference,String>{
}
