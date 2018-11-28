package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.TaskRule;
import com.hiekn.china.aeronautical.repository.TaskRepository;
import com.hiekn.china.aeronautical.service.TaskAsyncService;
import com.hiekn.china.aeronautical.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAsyncService taskAsyncService;

    @Override
    public Task add(TaskAdd taskAdd) {
        Task task = new Task();

        task.setTable(taskAdd.getTable());
        task.setKey(taskAdd.getKey());
        task.setName(taskAdd.getName());

        List<Rule> ruleList = new ArrayList<>();
        for (TaskRule t : taskAdd.getRules()) {
            Rule rule = new Rule();
            rule.setId(t.getId());
            rule.setName(t.getName());
            rule.setColumn(t.getColumn());
            rule.setRules(t.getRules());
            ruleList.add(rule);
        }
        task.setTaskRule(ruleList);

        Task target = taskRepository.save(task);
        taskAsyncService.taskAsyncSubmit(target);
        return target;
    }

    @Override
    public RestData<Task> findAll() {
        return new RestData<>(taskRepository.findAll(), taskRepository.count());
    }

    @Override
    public Task findOne(String id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task restart(String id) {
        Task target = taskRepository.findOne(id);
        taskAsyncService.taskAsyncSubmit(target);
        return target;
    }

    @Override
    public void delete(String id) {
        taskRepository.delete(id);
    }


}