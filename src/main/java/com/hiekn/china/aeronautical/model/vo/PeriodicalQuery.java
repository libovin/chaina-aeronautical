package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PeriodicalQuery extends PageQuery {
    /**
     * 按照期刊名称、翻译名称、中图刊号、CN号查询
     */

    @ApiModelProperty(value = "是否有错")
    private Boolean hasError;
}
