package com.hiekn.china.aeronautical.repository;

import com.hiekn.china.aeronautical.model.bean.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
