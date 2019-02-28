package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.china.aeronautical.knowledge.KgBaseService;
import com.hiekn.china.aeronautical.model.base.MarkError;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("taskAsyncService")
public class TaskAsyncServiceImpl implements TaskAsyncService {

    @Autowired
    private KgBaseService kgBaseService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Async(value = "customAsyncExecutor")
    public CompletableFuture<Task> taskAsyncSubmit(Task task) {
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


    private <T extends MarkError> Task runTask(Task task) {
        String table = task.getTable();
        String key = task.getKey();
        String kgName = task.getKgName();
        Class<T> tClass = getTableClass(table);
        List<T> all = kgBaseService.findAll(kgName, table, tClass);
        String dbName = kgBaseService.getKgNameMap().get(kgName);
        long promote = 0;
        long errorCount = 0;
        for (T obj : all) {
            if (checkSingle(obj, task.getTaskRule(), dbName)) {
                errorCount++;
            }
            promote++;
        }
        task.setPromote(promote);
        task.setErrorCount(errorCount);
        return task;
    }

    private <T extends MarkError> boolean checkSingle(T obj, List<Rule> rules, String collectionName) {
        Map<String, Boolean> errorMap = new HashMap<>();
        Map<String, Boolean> hasErrorMap = new HashMap<>();
        // Map errorMessage = new HashMap<>();
        boolean hasError = false;
        for (Rule rule : rules) {
            Map c = new HashMap();
            String column = rule.getColumn();
            String columnValue = "";
            try {
                Field field = obj.getClass().getDeclaredField(column);
                field.setAccessible(true);
                columnValue = (String) field.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Pattern pattern = RuleUtils.instance.getRulePattern(rule.getId());
            Matcher matcher = pattern.matcher(columnValue);
            Boolean flag = matcher.matches();
            errorMap.merge(column, flag, (a, b) -> a | b);
        }
        for (Map.Entry<String, Boolean> x : errorMap.entrySet()) {
            hasError = hasError | !x.getValue();
            hasErrorMap.put(x.getKey(), !x.getValue());
        }
        obj.setHasError(hasError);
        obj.setHasErrorTag(hasErrorMap);
        MarkError error = new MarkError();
        BeanUtils.copyProperties(obj, error);
        // obj.setMarkErrorResult(errorMessage);
        mongoTemplate.save(error, collectionName);
        return hasError;
    }


    private <T extends MarkError> Class<T> getTableClass(String table) {
        if (Objects.equals("conference", table)) {
            //noinspection unchecked
            return (Class<T>) Conference.class;
        } else if (Objects.equals("institution", table)) {
            //noinspection unchecked
            return (Class<T>) Institution.class;
        } else if (Objects.equals("periodical", table)) {
            //noinspection unchecked
            return (Class<T>) Periodical.class;
        } else if (Objects.equals("publisher", table)) {
            //noinspection unchecked
            return (Class<T>) Publisher.class;
        } else if (Objects.equals("website", table)) {
            //noinspection unchecked
            return (Class<T>) Website.class;
        }
        return null;
    }

}
