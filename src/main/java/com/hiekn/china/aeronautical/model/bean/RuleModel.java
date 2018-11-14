package com.hiekn.china.aeronautical.model.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RuleModel {

    @ApiModelProperty(value = "")
    private String type;

    private String name;

    private String value;
}
