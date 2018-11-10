package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.repository.custom.PeriodicalRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeriodicalRepository extends MongoRepository<Periodical, String>, PeriodicalRepositoryCustom {
}
