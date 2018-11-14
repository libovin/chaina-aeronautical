package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * 字典
 */
@Data
@Document
public class Dict extends Base {

    @ApiModelProperty(example = "conference", value = "表类型")
    private String table;

    @ApiModelProperty(example = "会议名称字典", value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典内容")
    private Set<String> text;
}
