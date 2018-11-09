package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Dataset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatasetRepository extends MongoRepository<Dataset,String> {
}
