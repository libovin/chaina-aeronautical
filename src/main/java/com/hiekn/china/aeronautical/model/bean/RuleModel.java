package com.hiekn.china.aeronautical.model.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class RuleModel {

    @ApiModelProperty(example = "regex",value = "规则类型")
    @NotBlank(message = "类型不能为空")
    @Pattern(regexp = "(regex|dict)" ,message = "类型必须为regex|dict")
    private String type;

    private String name;

    @ApiModelProperty(example = "\\d+",value = "类型regex为表达式，类型dict为Id")
    @NotBlank(message = "类型不能为空")
    private String value;
}
