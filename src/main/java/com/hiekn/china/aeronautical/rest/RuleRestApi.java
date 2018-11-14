package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.vo.RuleQuery;
import com.hiekn.china.aeronautical.service.RuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Component
@Path("rule")
@Api("规则")
@Produces(MediaType.APPLICATION_JSON)
public class RuleRestApi {

    @Autowired
    private RuleService ruleService;

    @ApiOperation("规则列表")
    @POST
    @Path("list")
    public RestResp<RestData<Rule>> findAll(@Valid RuleQuery query) {
        return new RestResp<>(ruleService.findAll(query));
    }

    @ApiOperation("规则详情")
    @GET
    @Path("{id}")
    public RestResp findOne(@PathParam("id") String id) {
        return new RestResp<>(ruleService.findOne(id));
    }

    @ApiOperation("删除规则")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        ruleService.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改规则")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Rule> modify(@PathParam("id") String id, @Valid Rule rule) {
        return new RestResp<>(ruleService.modify(id, rule));
    }

    @ApiOperation("新增规则")
    @POST
    @Path("add")
    public RestResp add(@Valid Rule rule) {
        return new RestResp<>(ruleService.add(rule));
    }

}
