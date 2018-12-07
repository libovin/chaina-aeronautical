package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.ConferenceService;
import com.hiekn.china.aeronautical.service.TaskService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Component
@Path("conference")
@Api("会议管理")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class ConferenceRestApi {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private TaskService taskService;

    private String collectionName = "conference";

    @ApiOperation("会议列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Conference>> findAll(
            @Valid ConferenceQuery conferenceQuery,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.findAll(conferenceQuery, collectionName + "_" + key));
    }

    @ApiOperation("会议详情")
    @GET
    @Path("{key}/{id}")
    public RestResp<Conference> findOne(
            @PathParam("id") String id,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除会议")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(
            @PathParam("id") String id,
            @PathParam("key") @DefaultValue("default") String key) {
        conferenceService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改会议")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Conference> modify(
            @PathParam("id") String id,
            @Valid Conference conference,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.modify(id, conference, collectionName + "_" + key));
    }

    @ApiOperation("新增会议")
    @POST
    @Path("{key}/add")
    public RestResp<Conference> add(
            @Valid Conference conference,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.add(conference, collectionName + "_" + key));
    }

    @ApiOperation("会议词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Conference>> wordStatistics(
            @Valid WordStatQuery wordStatQuery,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.wordStatistics(wordStatQuery, collectionName + "_" + key));
    }

    @ApiOperation("导入数据集")
    @POST
    @Path("{key}/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Map<String, Object>> importData(
            @BeanParam FileImport fileImport,
            @PathParam("key") @DefaultValue("default") String key) {
        Map<String, Object> map = conferenceService.importData(fileImport, collectionName + "_" + key);
        return new RestResp<>(map);
    }

    @ApiOperation("导出数据集")
    @GET
    @Path("{key}/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(@PathParam("key") @DefaultValue("default") String key) {
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                conferenceService.exportData(collectionName + "_" + key, output);
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = tinydata.xlsx")
                .build();
    }

    @ApiOperation("数据集检测结果统计")
    @POST
    @Path("{key}/check/stat")
    public RestResp<List<Map<String, Object>>> checkStat(
            @PathParam("key") @DefaultValue("default") String key) {
        List<Map<String, Object>> statDetailList = conferenceService.checkStat(key);
        return new RestResp<>(statDetailList);
    }


    @POST
    @Path("{key}/task/add")
    @ApiOperation("添加任务")
    public RestResp<Task> taskAdd(
            @PathParam("key") @DefaultValue("default") String key,
            @Valid TaskAdd taskAdd) {
        taskAdd.setKey(key);
        taskAdd.setTable(collectionName);
        return new RestResp<>(taskService.add(taskAdd));
    }


    @GET
    @ApiOperation("会议字段")
    @Path("column")
    public RestResp getColumn() {
        return new RestResp<>(DataBeanUtils.getClassProperty(Conference.class));
    }

}
