package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.TaskRule;
import com.hiekn.china.aeronautical.repository.TaskRepository;
import com.hiekn.china.aeronautical.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ThreadPoolTaskExecutor customAsyncExecutor;

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
        //TODO 执行任务
        taskAsyncSubmit(target);
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
        Task task = taskRepository.findOne(id);
        return task;
    }

    @Override
    public void delete(String id) {
        taskRepository.delete(id);
    }

    @Async(value="customAsyncExecutor")
    public CompletableFuture<Task> taskAsyncSubmit(Task task){
        return CompletableFuture.completedFuture(runTask(task));
    }


    public Task runTask(Task task){
        Task s = new Task();
        return s;
    }
}
