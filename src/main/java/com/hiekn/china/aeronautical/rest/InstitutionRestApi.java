package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.hiekn.china.aeronautical.service.InstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("institution")
@Api("研究机构管理")
@Produces(MediaType.APPLICATION_JSON)
public class InstitutionRestApi {

    @Autowired
    private InstitutionService institutionService;

    private String collectionName = "institution";

    @ApiOperation("研究机构列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Institution>> findAll(@Valid InstitutionQuery institutionQuery,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionService.findAll(institutionQuery, collectionName + "_" + key));
    }

    @ApiOperation("研究机构详情")
    @GET
    @Path("{key}/{id}")
    public RestResp findOne(@PathParam("id") String id,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除研究机构")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        institutionService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改研究机构")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Institution> modify(@PathParam("id") String id,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Institution institution) {
        return new RestResp<>(institutionService.modify(id, institution, collectionName + "_" + key));
    }

    @ApiOperation("新增研究机构")
    @POST
    @Path("{key}/add")
    public RestResp add(@Valid Institution institution,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionService.add(institution, collectionName + "_" + key));
    }

    @ApiOperation("研究机构统计")
    @POST
    @Path("{key}/word")
    public void wordStatistics(@PathParam("key") @DefaultValue("default") String key) {
        institutionService.wordStatistics(collectionName + "_" + key);
    }
}
