package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("scheduling")
@Api("任务调度")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SchedulingRestApi {

    @POST
    @Path("/add")
    @ApiOperation("添加统计任务")
    public RestResp<Boolean> skillAdd(){

        return new RestResp<>();
    }
}
