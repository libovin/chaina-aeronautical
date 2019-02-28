package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.KgDbInfo;
import com.hiekn.china.aeronautical.service.KgDbInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("kg")
@Api("图谱")
@Produces(MediaType.APPLICATION_JSON)
@Log
public class KgDbInfoRestApi {

    @Autowired
    private KgDbInfoService kgDbInfoService;

    @ApiOperation("图谱列表")
    @POST
    @Path("list")
    @Deprecated
    public RestResp<RestData<KgDbInfo>> findAll() {
        return new RestResp<>(kgDbInfoService.findAll());
    }

    @ApiOperation("图谱详情")
    @GET
    @Path("{id}")
    @Deprecated
    public RestResp<KgDbInfo> findOne(@PathParam("id") String id) {
        return new RestResp<>(kgDbInfoService.findOne(id));
    }

    @ApiOperation("删除图谱")
    @DELETE
    @Path("{id}")
    @Deprecated
    public RestResp delete(@PathParam("id") String id) {
        kgDbInfoService.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改图谱")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Deprecated
    public RestResp<KgDbInfo> modify(@PathParam("id") String id, @Valid KgDbInfo kgDbInfo) {
        return new RestResp<>(kgDbInfoService.modify(id, kgDbInfo));
    }

    @ApiOperation("新增图谱")
    @POST
    @Path("/")
    @Deprecated
    public RestResp<KgDbInfo> add(@Valid KgDbInfo kgDbInfo) {
        return new RestResp<>(kgDbInfoService.add(kgDbInfo));
    }

}
