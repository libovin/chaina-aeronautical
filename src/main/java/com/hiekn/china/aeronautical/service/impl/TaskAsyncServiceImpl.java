package com.hiekn.china.aeronautical.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
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
        String collectionName = table + "_" + key;
        CloseableIterator<T> closeableIterator = mongoTemplate.stream(new Query(), getTableClass(table), collectionName);

        int promote = 0;
        int errorCount = 0;
        while (closeableIterator.hasNext()) {
            T obj = closeableIterator.next();
            if (checkSingle(obj, task.getTaskRule(), collectionName)) {
                errorCount++;
            }
            promote++;
        }
        closeableIterator.close();
        task.setPromote(promote);
        task.setErrorCount(errorCount);
        return task;
    }

    private <T extends MarkError> boolean checkSingle(T obj, List<Rule> rules, String collectionName) {
        Map<String, Boolean> errorMap = new HashMap<>();
        Map<String, Boolean> hasErrorMap = new HashMap<>();
//        Map errorMessage = new HashMap<>();
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
//            List msgList = (List) errorMessage.computeIfAbsent(column, key -> new ArrayList<>());
//            c.put("column", column);
//            c.put("value", columnValue);
//            c.put("pattern", pattern.pattern());
//            c.put("result", flag);
//            msgList.add(c);
            errorMap.merge(column, flag, (a, b) -> a | b);
        }
        for (Map.Entry<String, Boolean> x : errorMap.entrySet()) {
            hasError = hasError | !x.getValue();
            hasErrorMap.put(x.getKey(), !x.getValue());
        }
        obj.setHasError(hasError);
        obj.setHasErrorTag(hasErrorMap);
        // obj.setMarkErrorResult(errorMessage);
        mongoTemplate.save(obj, collectionName);
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
