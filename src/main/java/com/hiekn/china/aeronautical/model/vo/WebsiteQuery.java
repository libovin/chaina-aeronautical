package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebsiteQuery extends PageQuery {
    /**
     * 网站名称
     */
    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "是否有错")
    private Boolean hasError;
}
