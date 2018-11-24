package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DictQuery {

    @ApiModelProperty(example = "conference", value = "数据集类型")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    private String key;

    @ApiModelProperty(example = "name", value = "列")
    private String column;

    @ApiModelProperty(example = "会议名称字典", value = "表类型")
    private String name;
}
