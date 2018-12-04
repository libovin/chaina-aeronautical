package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.bean.Task;
import com.hiekn.china.aeronautical.model.vo.FileImport;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.model.vo.TaskAdd;
import com.hiekn.china.aeronautical.model.vo.WordStatQuery;
import com.hiekn.china.aeronautical.service.PeriodicalService;
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
@Path("periodical")
@Api("期刊管理")
@Produces(MediaType.APPLICATION_JSON)
public class PeriodicalRestApi {

    @Autowired
    private PeriodicalService periodicalService;

    @Autowired
    private TaskService taskService;

    private String collectionName = "periodical";

    @ApiOperation("期刊列表")
    @POST
    @Path("{key}/list")
    public RestResp<RestData<Periodical>> findAll(@Valid PeriodicalQuery periodicalQuery,
                                                  @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(periodicalService.findAll(periodicalQuery, collectionName + "_" + key));
    }

    @ApiOperation("期刊详情")
    @GET
    @Path("{key}/{id}")
    public RestResp<Periodical> findOne(@PathParam("id") String id,
                            @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(periodicalService.findOne(id, collectionName + "_" + key));
    }

    @ApiOperation("删除期刊")
    @DELETE
    @Path("{key}/{id}")
    public RestResp delete(@PathParam("id") String id, @PathParam("key") @DefaultValue("default") String key) {
        periodicalService.delete(id, collectionName + "_" + key);
        return new RestResp<>();
    }

    @ApiOperation("修改期刊")
    @PUT
    @Path("{key}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Periodical> modify(@PathParam("id") String id,
                                       @PathParam("key") @DefaultValue("default") String key,
                                       @Valid Periodical periodical) {
        return new RestResp<>(periodicalService.modify(id, periodical, collectionName + "_" + key));
    }

    @ApiOperation("新增期刊")
    @POST
    @Path("{key}/add")
    public RestResp<Periodical> add(@Valid Periodical periodical,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(periodicalService.add(periodical, collectionName + "_" + key));
    }

    @ApiOperation("期刊词频统计")
    @POST
    @Path("{key}/word")
    public RestResp<RestData<Periodical>> wordStatistics(@Valid WordStatQuery wordStatQuery,
                               @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(periodicalService.wordStatistics(wordStatQuery,collectionName + "_" + key));
    }

    @ApiOperation("导入数据集")
    @POST
    @Path("{key}/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResp<Map<String, Object>> importData(
            @BeanParam FileImport fileImport,
            @PathParam("key") @DefaultValue("default") String key) {
        Map<String, Object> map = periodicalService.importData(fileImport,collectionName + "_" + key);
        return new RestResp<>(map);
    }

    @ApiOperation("导出数据集")
    @GET
    @Path("{key}/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(@PathParam("key") @DefaultValue("default") String key){

//        File dir = new File("custom/hangzhou_jw");
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//        File export = new File(dir.getAbsolutePath() + "/export.doc"); //默认在项目根目录下
//        if(!export.exists()){
//            try{
//                export.createNewFile();
//            }catch(IOException e){
//                System.out.println("创建文件失败");
//            }
//        }
//
//        String mt = new MimetypesFileTypeMap().getContentType(export);
//        return Response
//                .ok(export, mt)
//                .header("Content-disposition","attachment;filename=x.doc")
//                .header("Cache-Control", "no-cache").build();
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                periodicalService.exportData("", output);
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
    public RestResp<List<Map<String, Object>>> checkStat(@PathParam("key") @DefaultValue("default") String key){
        List<Map<String, Object>> statDetailList= periodicalService.checkStat(key);
        return new RestResp<>(statDetailList);
    }

    @POST
    @Path("{key}/task/add")
    @ApiOperation("添加任务")
    public RestResp<Task> taskAdd(
            @PathParam("key") @DefaultValue("default") String key,
            @Valid TaskAdd taskAdd){
        taskAdd.setKey(key);
        taskAdd.setTable(collectionName);
        return new RestResp<>(taskService.add(taskAdd));
    }


    @GET
    @ApiOperation("期刊字段")
    @Path("column")
    public RestResp getColumn(){
        return new RestResp<>(DataBeanUtils.getClassProperty(Periodical.class));
    }

}
