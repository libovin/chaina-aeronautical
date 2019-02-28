package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.knowledge.InstitutionKgService;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.bean.Institution;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.InstitutionQuery;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.WordMarkError;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.hiekn.china.aeronautical.service.InstitutionService;
import com.hiekn.china.aeronautical.service.TaskService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.HttpUtils;
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
import javax.ws.rs.HeaderParam;
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
@Path("institution")
@Api("研究机构管理")
@Produces(MediaType.APPLICATION_JSON)
public class InstitutionRestApi {

    @Autowired
    private InstitutionKgService institutionKgService;


    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DatasetService datasetService;

    private String collectionName = "institution";

    @ApiOperation("研究机构列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Institution>> findAll(
            @Valid InstitutionQuery institutionQuery,
            @HeaderParam("kgName") String kgName,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionKgService.page(kgName, institutionQuery));
    }

    @ApiOperation("研究机构详情")
    @GET
    @Path("{key}/{id}")
    public RestResp<Institution> findOne(
            @PathParam("id") Long id,
            @HeaderParam("kgName") String kgName,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionKgService.findOne(kgName, id));
    }

    @ApiOperation("删除研究机构")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(
            @PathParam("id") Long id,
            @HeaderParam("kgName") String kgName,
            @PathParam("key") @DefaultValue("default") String key) {
        institutionKgService.delete(kgName, id);
        return new RestResp<>();
    }

    @ApiOperation("修改研究机构")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Institution> modify(
            @PathParam("id") Long id,
            @HeaderParam("kgName") String kgName,
            @Valid Institution institution,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionKgService.modify(kgName, id, institution));
    }

    @ApiOperation("新增研究机构")
    @POST
    @Path("{key}/add")
    public RestResp<Institution> add(
            @Valid Institution institution,
            @HeaderParam("kgName") String kgName,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionKgService.insert(kgName, institution));
    }

    @ApiOperation("研究机构词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Institution>> wordStatistics(
            @Valid WordStatQuery wordStatQuery,
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(institutionService.wordStatistics(wordStatQuery, collectionName + "_" + key));
    }

    @ApiOperation("会议统计标错")
    @POST
    @Path("{key}/word/markerror")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Integer> markerror(
            @PathParam("key") @DefaultValue("default") String key,
            WordMarkError wordMarkError) {
        return new RestResp<>(institutionService.wordMarkError(wordMarkError, collectionName + "_" + key));
    }

    @ApiOperation("导入数据集")
    @POST
    @Path("{key}/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Map<String, Object>> importData(
            @BeanParam FileImport fileImport,
            @PathParam("key") @DefaultValue("default") String key) {
        Map<String, Object> map = institutionService.importData(fileImport, collectionName + "_" + key);
        return new RestResp<>(map);
    }

    @ApiOperation("导出数据集")
    @GET
    @Path("{key}/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(
            @PathParam("key") @DefaultValue("default") String key) {
        Dataset dataset = datasetService.findFirstByTypeKey(collectionName + "_" + key);
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                institutionService.exportData(collectionName + "_" + key, output);
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + HttpUtils.UTF_8toISO_8859_1(dataset.getName()) + ".xlsx")
                .build();
    }

    @ApiOperation("数据集检测结果统计")
    @POST
    @Path("{key}/check/stat")
    @Deprecated
    public RestResp<List<Map<String, Object>>> checkStat(
            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>();
    }

    @POST
    @Path("{key}/task/add")
    @ApiOperation("添加标错任务")
    public RestResp<Task> taskAdd(
            @PathParam("key") @DefaultValue("default") String key,
            @HeaderParam("kgName") String kgName,
            @Valid TaskAdd taskAdd) {
        taskAdd.setKey(key);
        taskAdd.setTable(collectionName);
        taskAdd.setKgName(kgName);
        return new RestResp<>(taskService.add(taskAdd));
    }

    @GET
    @ApiOperation("研究机构字段")
    @Path("column")
    public RestResp getColumn() {
        return new RestResp<>(DataBeanUtils.getClassProperty(Institution.class));
    }

}
