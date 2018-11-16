package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.vo.DictQuery;
import com.hiekn.china.aeronautical.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("dict")
@Api("字典")
@Produces(MediaType.APPLICATION_JSON)
public class DictRestApi {

    @Autowired
    private DictService dictService;

    @ApiOperation("字典列表")
    @POST
    @Path("list")
    public RestResp<RestData<Dict>> findAll(@Valid DictQuery query) {
        return new RestResp<>(dictService.findAll(query));
    }

    @ApiOperation("字典详情")
    @GET
    @Path("{id}")
    public RestResp<Dict> findOne(@PathParam("id") String id) {
        return new RestResp<>(dictService.findOne(id));
    }

    @ApiOperation("删除字典")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        //dictService.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改字典")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Dict> modify(@PathParam("id") String id, @Valid Dict dict) {
        return new RestResp<>(dictService.modify(id, dict));
    }

    @ApiOperation("新增字典")
    @POST
    @Path("add")
    public RestResp<Dict> add(@Valid Dict dict) {
        return new RestResp<>(dictService.add(dict));
    }

}
