package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Website;
import com.hiekn.china.aeronautical.model.vo.WebsiteQuery;
import com.hiekn.china.aeronautical.repository.WebsiteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Controller
@Path("website")
@Produces(MediaType.APPLICATION_JSON)
@Api("网站管理")
public class WebsiteRestApi {

    @Autowired
    private WebsiteRepository websiteRepository;

    private String collectionName;

    @ApiOperation("会议列表")
    @GET
    @Path("/list")
    public RestResp<RestData> get(@DefaultValue("1") @QueryParam("page") Integer page,
                                  @DefaultValue("10") @QueryParam("size") Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Website> p = websiteRepository.findAll(pageable, collectionName);
        return new RestResp<>(new RestData<>(p.getContent(), p.getTotalElements()));
    }

    @ApiOperation("会议详情")
    @GET
    @Path("{id}")
    public RestResp findOne(@PathParam("id") String id) {
        return new RestResp<>(websiteRepository.findOne(id, collectionName));
    }

    @ApiOperation("删除会议")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        websiteRepository.delete(id, collectionName);
        return new RestResp<>();
    }

    @ApiOperation("修改会议")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp modify(@PathParam("id") String id,@Valid WebsiteQuery website) {
        Website targe = websiteRepository.findOne(id, collectionName);
        BeanUtils.copyProperties(website, targe);
        return new RestResp<>(websiteRepository.save(targe, collectionName));
    }

    @ApiOperation("新增会议")
    @POST
    @Path("add")
    public RestResp add(@Valid WebsiteQuery website) {
        Website targe = new Website();
        BeanUtils.copyProperties(website, targe);
        return new RestResp<>(websiteRepository.save(targe, collectionName));
    }
}
