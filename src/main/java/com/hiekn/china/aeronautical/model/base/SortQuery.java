package com.hiekn.china.aeronautical.model.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

@Data
public class SortQuery {
    @ApiModelProperty(example = "createTime", value = "属性")
    @QueryParam("order")
    @NotBlank(message = "属性不能为空")
    private String order;

    @ApiModelProperty(example = "desc", value = "顺序/倒序")
    @Pattern(regexp = "^(asc|desc)$", message = "必须是 'asc' 或者 'desc' ")
    @QueryParam("asc")
    private String by;
}
