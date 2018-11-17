package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.WebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
    public RestResp<Website> findOne(@PathParam("id") String id,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除网站")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        //websiteService.delete(id, collectionName + "_" + key);
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
    public RestResp<Website> add(@Valid Website website,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.add(website, collectionName + "_" + key));
    }

    @ApiOperation("网站词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Website>> wordStatistics(@Valid WordStatQuery wordStatQuery,
                               @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(websiteService.wordStatistics(wordStatQuery,collectionName + "_" + key));
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
        List<Map<String, Object>> statDetailList = websiteService.checkStat(key);
        return new RestResp<>(statDetailList);
    }
}
