package com.hiekn.china.aeronautical.service;

import com.hiekn.china.aeronautical.model.bean.Task;

import java.util.concurrent.CompletableFuture;

public interface TaskAsyncService {
    CompletableFuture<Task> taskAsyncSubmit(Task task);
}
