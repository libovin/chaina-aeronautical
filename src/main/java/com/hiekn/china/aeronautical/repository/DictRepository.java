package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Dict;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DictRepository extends MongoRepository<Dict, String> {
}
