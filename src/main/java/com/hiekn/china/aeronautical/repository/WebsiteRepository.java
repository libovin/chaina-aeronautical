package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.repository.custom.mongo.CustomMongoRepository;

public interface WebsiteRepository extends CustomMongoRepository<Website,String>{
}
