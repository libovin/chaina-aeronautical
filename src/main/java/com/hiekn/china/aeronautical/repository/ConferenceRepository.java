package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.repository.custom.ConferenceRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConferenceRepository extends MongoRepository<Conference,String>, ConferenceRepositoryCustom {
}
