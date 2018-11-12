package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.List;

@Data
@Document
public class Dataset extends Base {

    @ApiModelProperty(example = "default", value = "数据集key")
    @Indexed(unique = true)
    @NotBlank(message = "key不能为空")
    private String key;

    @ApiModelProperty(example = "期刊数据集", value = "数据集名称")
    private String name;

    @ApiModelProperty(example = "admin", value = "拥有者")
    @Indexed
    private String owner;

    @ApiModelProperty(example = "group", value = "组")
    private String group;

    @Valid
    @ApiModelProperty(value = "字典集")
    private List<Dict> dicts;

    @Valid
    @ApiModelProperty(value = "规则集")
    private List<Rule> rules;

    @Valid
    @ApiModelProperty(value = "标错集")
    private List<MarkError> errors;
}
