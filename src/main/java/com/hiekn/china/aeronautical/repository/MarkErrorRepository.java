package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.MarkError;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarkErrorRepository extends MongoRepository<MarkError,String> {
}
