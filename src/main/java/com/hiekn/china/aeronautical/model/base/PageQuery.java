package com.hiekn.china.aeronautical.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;

@Data
public class PageQuery {

    @DefaultValue("10")
    @ApiModelProperty(example = "10")
    @Min(1)
    private Integer pageSize;

    @DefaultValue("1")
    @ApiModelProperty(example = "1")
    @Min(1)
    private Integer pageNo;

}
