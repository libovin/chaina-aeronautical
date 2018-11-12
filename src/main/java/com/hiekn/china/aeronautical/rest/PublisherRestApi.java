package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Publisher;
import com.hiekn.china.aeronautical.model.vo.PublisherQuery;
import com.hiekn.china.aeronautical.service.PublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("publisher")
@Api("出版机构管理")
@Produces(MediaType.APPLICATION_JSON)
public class PublisherRestApi {

    @Autowired
    private PublisherService publisherService;

    private String collectionName = "publisher";

    @ApiOperation("出版机构列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Publisher>> findAll(@Valid PublisherQuery publisherQuery,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(publisherService.findAll(publisherQuery, collectionName + "_" + key));
    }

    @ApiOperation("出版机构详情")
    @GET
    @Path("{key}/{id}")
    public RestResp findOne(@PathParam("id") String id,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(publisherService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除出版机构")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        publisherService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改出版机构")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Publisher> modify(@PathParam("id") String id,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Publisher publisher) {
        return new RestResp<>(publisherService.modify(id, publisher, collectionName + "_" + key));
    }

    @ApiOperation("新增出版机构")
    @POST
    @Path("{key}/add")
    public RestResp add(@Valid Publisher publisher,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(publisherService.add(publisher, collectionName + "_" + key));
    }

    @ApiOperation("出版机构统计")
    @POST
    @Path("{key}/word")
    public void wordStatistics(@PathParam("key") @DefaultValue("default") String key) {
        publisherService.wordStatistics(collectionName + "_" + key);
    }
}
