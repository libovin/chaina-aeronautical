package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class RuleQuery {

    @ApiModelProperty(example = "表名", value = "表名")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    @Indexed
    private String key;

    @ApiModelProperty(example = "数字规则", value = "规则名")
    private String name;
}
