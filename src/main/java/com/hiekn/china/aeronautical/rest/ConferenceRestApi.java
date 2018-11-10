package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.service.ConferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("conference")
@Api("会议管理")
@Produces(MediaType.APPLICATION_JSON)
public class ConferenceRestApi {

    @Autowired
    private ConferenceService conferenceService;

    private String collectionName = "conference";

    @ApiOperation("会议列表")
    @POST
    @Path("/list")
    public RestResp<RestData<Conference>> get(@Valid ConferenceQuery conferenceQuery) {
        return new RestResp<>(conferenceService.findAll(conferenceQuery, collectionName));
    }

    @ApiOperation("会议详情")
    @GET
    @Path("{id}")
    public RestResp findOne(@PathParam("id") String id) {
        return new RestResp<>(conferenceService.findOne(id, collectionName));
    }

    @ApiOperation("删除会议")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        conferenceService.delete(id, collectionName);
        return new RestResp<>();
    }

    @ApiOperation("修改会议")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Conference> modify(@PathParam("id") String id, @Valid Conference conference) {
        return new RestResp<>(conferenceService.modify(id, conference, collectionName));
    }

    @ApiOperation("新增会议")
    @POST
    @Path("add")
    public RestResp add(@Valid Conference conference) {
        return new RestResp<>(conferenceService.add(conference, collectionName));
    }


    @POST
    @Path("word")
    public void wordStatistics(){
        conferenceService.wordStatistics();
    }
}
