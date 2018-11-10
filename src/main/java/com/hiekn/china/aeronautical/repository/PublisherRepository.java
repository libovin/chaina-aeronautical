package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.repository.custom.PublisherRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublisherRepository extends MongoRepository<Publisher,String>, PublisherRepositoryCustom {
}
