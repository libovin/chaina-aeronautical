package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token,String> {
}
