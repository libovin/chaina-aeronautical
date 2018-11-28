package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.service.TaskAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service("taskAsyncService")
public class TaskAsyncServiceImpl implements TaskAsyncService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Async(value="customAsyncExecutor")
    public CompletableFuture<Task> taskAsyncSubmit(Task task){
        try {
            Thread.sleep(5000);
        }catch ( Exception e) {

        }
       return CompletableFuture.completedFuture(runTask(task));
    }


    private Task runTask(Task task){
        Task s = new Task();
        String table = task.getTable();
        String key = task.getKey();

        mongoTemplate.findAll(getTableClass(table),table+"_"+key);
        return s;
    }

   private Class getTableClass(String table) {
        if(Objects.equals("conference",table)){
            return Conference.class;
        } else if(Objects.equals("institution",table)){
            return Institution.class;
        }else if(Objects.equals("periodical",table)){
            return Periodical.class;
        }else if(Objects.equals("publisher",table)){
            return Publisher.class;
        }else if(Objects.equals("website",table)){
            return Website.class;
        }
        return null;
    }
}
