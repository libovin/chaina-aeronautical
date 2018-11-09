package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RuleRepository extends MongoRepository<Rule,String> {
}
