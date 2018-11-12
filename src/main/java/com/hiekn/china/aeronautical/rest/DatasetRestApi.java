package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.service.DatasetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("dataset")
@Api("数据集")
@Produces(MediaType.APPLICATION_JSON)
public class DatasetRestApi {

    @Autowired
    private DatasetService datasetService;

    @ApiOperation("数据集列表")
    @GET
    @Path("list")
    public RestResp<RestData<Dataset>> findAll() {
        return new RestResp<>(datasetService.findAll());
    }

    @ApiOperation("数据集详情")
    @GET
    @Path("{id}")
    public RestResp findOne(@PathParam("id") String id) {
        return new RestResp<>(datasetService.findOne(id));
    }

    @ApiOperation("删除数据集")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        datasetService.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改数据集")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Dataset> modify(@PathParam("id") String id, @Valid Dataset dataset) {
        return new RestResp<>(datasetService.modify(id, dataset));
    }

    @ApiOperation("新增数据集")
    @POST
    @Path("add")
    public RestResp add(@Valid Dataset dataset) {
        return new RestResp<>(datasetService.add(dataset));
    }

}
