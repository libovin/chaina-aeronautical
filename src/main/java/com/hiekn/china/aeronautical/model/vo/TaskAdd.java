package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaskAdd {

    @ApiModelProperty(hidden = true)
    private String table;

    @ApiModelProperty(hidden = true)
    private String key;

    @ApiModelProperty("任务名称")
    private String name;

    private String kgName;

    @ApiModelProperty("规则")
    private List<TaskRule> rules;
}
