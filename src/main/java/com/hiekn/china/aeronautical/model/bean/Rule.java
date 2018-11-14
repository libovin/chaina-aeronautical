package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 规则
 */
@Data
@Document
public class Rule extends Base {

    @ApiModelProperty(example = "表名", value = "表名")
    private String table;

    private String name;

    @ApiModelProperty(value = "")
    private String type;

    @ApiModelProperty(example = "^\\d$", value = "表达式")
    private String regex;
}
