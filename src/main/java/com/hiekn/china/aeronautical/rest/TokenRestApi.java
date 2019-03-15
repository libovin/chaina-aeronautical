package com.hiekn.china.aeronautical.rest;

import com.hiekn.boot.autoconfigure.base.model.result.RestResp;
import com.hiekn.china.aeronautical.model.bean.Token;
import com.hiekn.china.aeronautical.repository.TokenRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("token")
@Api("Token")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TokenRestApi {
    @Autowired
    private TokenRepository tokenRepository;

    @ApiOperation("Token列表")
    @POST
    @Path("/list")
    public RestResp<List<Token>> findAll() {
        return new RestResp<>(tokenRepository.findAll());
    }

    @ApiOperation("Token详情")
    @GET
    @Path("{id}")
    public RestResp<Token> findOne(@PathParam("id") String id) {
        return new RestResp<>(tokenRepository.findOne(id));
    }

    @ApiOperation("删除网站")
    @DELETE
    @Path("{id}")
    public RestResp delete(@PathParam("id") String id) {
        tokenRepository.delete(id);
        return new RestResp<>();
    }

    @ApiOperation("修改网站")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResp<Token> modify(@PathParam("id") String id,
                                  @Valid Token website) {
        return new RestResp<>(tokenRepository.save(website));
    }

    @ApiOperation("新增网站")
    @POST
    @Path("{key}/add")
    public RestResp<Token> add(@Valid Token website) {
        return new RestResp<>(tokenRepository.insert(website));
    }
}
