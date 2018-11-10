package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.repository.custom.WebsiteRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WebsiteRepository extends MongoRepository<Website,String>, WebsiteRepositoryCustom {
}
