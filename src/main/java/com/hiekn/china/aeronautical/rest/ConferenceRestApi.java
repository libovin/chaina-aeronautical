package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Conference;
import com.hiekn.china.aeronautical.model.vo.ConferenceQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.ConferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Component
@Path("conference")
@Api("会议管理")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class ConferenceRestApi {

    @Autowired
    private ConferenceService conferenceService;

    private String collectionName = "conference";

    @ApiOperation("会议列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Conference>> findAll(@Valid ConferenceQuery conferenceQuery,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.findAll(conferenceQuery, collectionName + "_" + key));
    }

    @ApiOperation("会议详情")
    @GET
    @Path("{key}/{id}")
    public RestResp<Conference> findOne(@PathParam("id") String id,
                                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除会议")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        //conferenceService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改会议")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Conference> modify(@PathParam("id") String id,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Conference conference) {
        return new RestResp<>(conferenceService.modify(id, conference, collectionName + "_" + key));
    }

    @ApiOperation("新增会议")
    @POST
    @Path("{key}/add")
    public RestResp<Conference> add(@Valid Conference conference,
                                    @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.add(conference, collectionName + "_" + key));
    }

    @ApiOperation("会议词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Conference>> wordStatistics(@Valid WordStatQuery wordStatQuery,
                                                         @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(conferenceService.wordStatistics(wordStatQuery, collectionName + "_" + key));
    }

    @ApiOperation("导入数据集")
    @POST
    @Path("{key}/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Map<String, Object>> importData(@PathParam("key") @DefaultValue("default") String key,
                                                    @ApiParam(value = "file") @FormDataParam("filename") FormDataContentDisposition fileInfo,
                                                    @ApiParam(value = "file") @FormDataParam("filename") FormDataBodyPart formDataBodyPart,
                                                    @ApiParam(value = "file") @FormDataParam("filename") InputStream fileIn) {
        Map<String, Object> map = conferenceService.importData(fileInfo, fileIn, formDataBodyPart);
        return new RestResp<>(map);
    }

    @ApiOperation("导出数据集")
    @GET
    @Path("{key}/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(@PathParam("key") @DefaultValue("default") String key) {
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                try {
                    java.nio.file.Path path = Paths.get("D:/tinydata.xlsx");
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } catch (Exception e) {
                    throw new WebApplicationException("File Not Found !!");
                }
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
    public RestResp<List<Map<String, Object>>> checkStat(@PathParam("key") @DefaultValue("default") String key) {
        List<Map<String, Object>> statDetailList = conferenceService.checkStat(key);
        return new RestResp<>(statDetailList);
    }

}
