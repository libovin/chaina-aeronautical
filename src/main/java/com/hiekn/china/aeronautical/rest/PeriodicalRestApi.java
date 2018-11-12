package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Periodical;
import com.hiekn.china.aeronautical.model.vo.PeriodicalQuery;
import com.hiekn.china.aeronautical.service.PeriodicalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("periodical")
@Api("期刊管理")
@Produces(MediaType.APPLICATION_JSON)
public class PeriodicalRestApi {

    @Autowired
    private PeriodicalService periodicalService;

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
    public RestResp findOne(@PathParam("id") String id,
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
    public RestResp add(@Valid Periodical periodical,
                        @PathParam("key") @DefaultValue("default") String key) {
        return new RestResp<>(periodicalService.add(periodical, collectionName + "_" + key));
    }

    @ApiOperation("期刊统计")
    @POST
    @Path("{key}/word")
    public void wordStatistics(@PathParam("key") @DefaultValue("default") String key) {
        periodicalService.wordStatistics(collectionName + "_" + key);
    }
}
