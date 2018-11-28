package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;

public interface TaskService {
    Task add(TaskAdd task);

    RestData<Task> findAll();

    Task findOne(String id);

    Task restart(String id);

    void delete(String id);
}
