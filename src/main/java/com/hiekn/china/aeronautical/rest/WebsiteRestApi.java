package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.knowledge.WebsiteKgService;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.model.vo.WordMarkError;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.DatasetService;
import com.hiekn.china.aeronautical.service.TaskService;
import com.hiekn.china.aeronautical.service.WebsiteService;
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
@Path("website")
@Api("网站管理")
@Produces(MediaType.APPLICATION_JSON)
public class WebsiteRestApi {

    @Autowired
    private WebsiteKgService websiteKgService;

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DatasetService datasetService;

    private String collectionName = "website";

    @ApiOperation("网站列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Website>> findAll(@Valid WebsiteQuery websiteQuery,
                                               @HeaderParam("kgName") String kgName,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.findAll(websiteQuery, collectionName + "_" + key));
    }

    @ApiOperation("网站详情")
    @GET
    @Path("{key}/{id}")
    public RestResp<Website> findOne(@PathParam("id") Long id,
                                     @HeaderParam("kgName") String kgName,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteKgService.findOne(kgName, id));
    }

    @ApiOperation("删除网站")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") Long id,
                           @HeaderParam("kgName") String kgName,
                           @PathParam("key") @DefaultValue("default") String key) {
        websiteKgService.delete(kgName, id);
        return new RestResp<>();
    }

    @ApiOperation("修改网站")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Website> modify(@PathParam("id") Long id,
                                    @HeaderParam("kgName") String kgName,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Website website) {
        return new RestResp<>(websiteKgService.modify(kgName, id,website));
    }

    @ApiOperation("新增网站")
    @POST
    @Path("{key}/add")
    public RestResp<Website> add(@Valid Website website,
                                 @HeaderParam("kgName") String kgName,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteKgService.insert(kgName,website));
    }

    @ApiOperation("网站词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Website>> wordStatistics(@Valid WordStatQuery wordStatQuery,
                               @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.wordStatistics(wordStatQuery,collectionName + "_" + key));
    }

    @ApiOperation("会议统计标错")
    @POST
    @Path("{key}/word/markerror")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Integer> markerror(
            @PathParam("key") @DefaultValue("default") String key,
            WordMarkError wordMarkError) {
        return new RestResp<>(websiteService.wordMarkError(wordMarkError, collectionName + "_" + key));
    }

    @ApiOperation("导入数据集")
    @POST
    @Path("{key}/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Map<String, Object>> importData(
            @BeanParam FileImport fileImport,
            @PathParam("key") @DefaultValue("default") String key) {
        Map<String, Object> map = websiteService.importData(fileImport,collectionName + "_" + key);
        return new RestResp<>(map);
    }

    @ApiOperation("导出数据集")
    @GET
    @Path("{key}/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(@PathParam("key") @DefaultValue("default") String key) {

//        File dir = new File("custom/hangzhou_jw");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File export = new File(dir.getAbsolutePath() + "/export.doc"); //默认在项目根目录下
//        if (!export.exists()) {
//            try {
//                export.createNewFile();
//            } catch (IOException e) {
//                System.out.println("创建文件失败");
//            }
//        }
//
//        String mt = new MimetypesFileTypeMap().getContentType(export);
//        return Response
//                .ok(export, mt)
//                .header("Content-disposition", "attachment;filename=x.xls")
//                .header("Cache-Control", "no-cache").build();
        Dataset dataset = datasetService.findFirstByTypeKey(collectionName + "_" + key);
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                websiteService.exportData(collectionName + "_" + key, output);
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
    public RestResp<List<Map<String, Object>>> checkStat(@PathParam("key") @DefaultValue("default") String key) {
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
    @ApiOperation("网站字段")
    @Path("column")
    public RestResp getColumn(){
        return new RestResp<>(DataBeanUtils.getClassProperty(Website.class));
    }
}
