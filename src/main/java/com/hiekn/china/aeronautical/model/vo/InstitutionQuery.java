package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class InstitutionQuery extends PageQuery {
    /**
     * 按照机构名称、研究领域等查询
     */

    @ApiModelProperty(value = "研究机构名称")
    private String name;

    @ApiModelProperty(value = "研究领域")
    private String researchField;
}
