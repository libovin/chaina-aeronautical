package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.repository.custom.mongo.CustomMongoRepository;

public interface PeriodicalRepository extends CustomMongoRepository<Conference,String>{
}
