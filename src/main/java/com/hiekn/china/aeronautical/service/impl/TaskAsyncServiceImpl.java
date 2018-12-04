package com.hiekn.china.aeronautical.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.repository.TaskRepository;
import com.hiekn.china.aeronautical.service.TaskAsyncService;
import com.hiekn.china.aeronautical.util.RuleUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("taskAsyncService")
public class TaskAsyncServiceImpl implements TaskAsyncService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TaskRepository taskRepository;

    @Async(value = "customAsyncExecutor")
    public CompletableFuture<Task> taskAsyncSubmit(Task task) {

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(runTask(task)).whenComplete((t, e) -> {
            if (e == null) {
                t.setStatus(1);
                taskRepository.save(t);
            } else {
                t.setStatus(2);
                taskRepository.save(t);
                throw new RuntimeException(e);
            }
        });
    }


    private Task runTask(Task task) {
        String table = task.getTable();
        String key = task.getKey();
        String collectionName = table + "_" + key;
        DBCollection dbCollection = mongoTemplate.getCollection(collectionName);
        DBCursor dbCursor = dbCollection.find();
        task.setPromote(dbCursor.count());
        int errorCount = 0;
        while (dbCursor.hasNext()) {
            BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
            if (!checkSingle(dbObject.toJson(), task.getTaskRule(), collectionName)) {
                errorCount++;
            }
        }
        dbCursor.close();
        task.setErrorCount(errorCount);
        return task;
    }

    private boolean checkSingle(String jsonString, List<Rule> rules, String collectionName) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Object id = jsonObject.getJSONObject("_id").get("$oid");
        Map<String, Boolean> errorMap = new ConcurrentHashMap<>();
        boolean hasError = true;
        for (Rule rule : rules) {
            String column = rule.getColumn();
            String columnValue = (String) jsonObject.get(column);
            Pattern pattern = RuleUtils.instance.getRulePattern(rule.getId());
            Matcher matcher = pattern.matcher(columnValue);
            Boolean flag = matcher.matches();
            hasError = flag & hasError;
            errorMap.merge(column, flag, (a, b) -> a | b);
        }
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("errorTag", errorMap);
        if (!hasError) {
            update.set("hasError", true);
            mongoTemplate.updateFirst(query, update, collectionName);
        }
        return hasError;
    }

    private Class getTableClass(String table) {
        if (Objects.equals("conference", table)) {
            return Conference.class;
        } else if (Objects.equals("institution", table)) {
            return Institution.class;
        } else if (Objects.equals("periodical", table)) {
            return Periodical.class;
        } else if (Objects.equals("publisher", table)) {
            return Publisher.class;
        } else if (Objects.equals("website", table)) {
            return Website.class;
        }
        return null;
    }
}
