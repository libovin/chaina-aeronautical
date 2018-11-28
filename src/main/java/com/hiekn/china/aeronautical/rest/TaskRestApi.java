package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("task")
@Api("任务中心")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TaskRestApi {

    @Autowired
    private TaskService taskService;

    @GET
    @Path("list")
    @ApiOperation("任务列表")
    public RestResp<RestData<Task>> list() {
        return new RestResp<>(taskService.findAll());
    }

    @POST
    @Path("restart/{id}")
    @ApiOperation("重新运行")
    public RestResp<Task> restart(@PathParam("id") String id) {
        return new RestResp<>(taskService.restart(id));
    }

    @GET
    @Path("{id}")
    @ApiOperation("任务详情")
    public RestResp<Task> get(@PathParam("id") String id) {
        return new RestResp<>(taskService.findOne(id));
    }

    @DELETE
    @Path("{id}")
    @ApiOperation("删除任务")
    public RestResp<String> delete(@PathParam("id") String id) {
        taskService.delete(id);
        return new RestResp<>("删除成功");
    }

}
