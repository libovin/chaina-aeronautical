package com.hiekn.china.aeronautical.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public abstract class MarkError extends Base {

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map errorTag;
}
