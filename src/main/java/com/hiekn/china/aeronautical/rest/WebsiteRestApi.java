package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.service.WebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("website")
@Api("网站管理")
@Produces(MediaType.APPLICATION_JSON)
public class WebsiteRestApi {

    @Autowired
    private WebsiteService websiteService;

    private String collectionName = "website";

    @ApiOperation("网站列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Website>> findAll(@Valid WebsiteQuery websiteQuery,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.findAll(websiteQuery, collectionName + "_" + key));
    }

    @ApiOperation("网站详情")
    @GET
    @Path("{key}/{id}")
    public RestResp findOne(@PathParam("id") String id,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除网站")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        websiteService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改网站")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Website> modify(@PathParam("id") String id,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Website website) {
        return new RestResp<>(websiteService.modify(id, website, collectionName + "_" + key));
    }

    @ApiOperation("新增网站")
    @POST
    @Path("{key}/add")
    public RestResp add(@Valid Website website,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.add(website, collectionName + "_" + key));
    }

    @ApiOperation("网站统计")
    @POST
    @Path("{key}/word")
    public void wordStatistics(@PathParam("key") @DefaultValue("default") String key) {
        websiteService.wordStatistics(collectionName + "_" + key);
    }
}
