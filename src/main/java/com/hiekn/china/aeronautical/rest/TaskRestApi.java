package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("task")
@Api("任务中心")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TaskRestApi {


    @GET
    @Path("list")
    @ApiOperation("任务列表")
    public RestResp<RestData<Task>> list(){

        return new RestResp<>();
    }

    @POST
    @Path("restart/{id}")
    public RestResp<Boolean> restart() {

        return new RestResp<>();
    }


    @GET
    @Path("{id}")
    public RestResp<Task> get() {

        return new RestResp<>();
    }




}
