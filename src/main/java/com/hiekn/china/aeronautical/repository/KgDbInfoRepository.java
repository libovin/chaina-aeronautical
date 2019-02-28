package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.KgDbInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KgDbInfoRepository extends MongoRepository<KgDbInfo,String> {

}
