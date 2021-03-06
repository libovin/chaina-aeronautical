package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskRule {

    @ApiModelProperty("规则Id")
    private String id;

    @ApiModelProperty(example = "name", value = "列")
    private String column;

//    @ApiModelProperty(example = "数字规则", value = "规则名")
//    private String name;
//
//    @ApiModelProperty(value = "表达式列表")
//    private List<RuleModel> rules;
}
