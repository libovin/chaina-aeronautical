package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ApiModel("任务")
public class Task extends Base {

    @ApiModelProperty(hidden = true)
    private String typeKey;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("规则")
    private Object rules;

    /**
     * 状态
     */
    @ApiModelProperty(hidden = true)
    private Integer status;

    /**
     * 提升度
     */
    @ApiModelProperty(hidden = true)
    private Integer promote;

    /**
     * 纠错量
     */
    @ApiModelProperty(hidden = true)
    private Integer errorCount;
}
