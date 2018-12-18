package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PublisherQuery extends PageQuery {
    /**
     * 机构名称
     */

    @ApiModelProperty(value = "是否有错")
    private Boolean hasError;
}
