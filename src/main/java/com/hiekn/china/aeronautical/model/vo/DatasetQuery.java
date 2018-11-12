package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class DatasetQuery extends PageQuery {

    @ApiModelProperty(example = "conference", value = "数据集类型")
    @NotBlank(message = "数据集类型不能为空")
    @Pattern(regexp = "(conference|institution|periodical|publisher|website)",
            message = "数据集类型必须为conference|institution|periodical|publisher|website")
    private String type;

    @ApiModelProperty(example = "default", value = "数据集key")
    @NotBlank(message = "key不能为空")
    private String key;

    @ApiModelProperty(example = "期刊数据集", value = "数据集名称")
    private String name;

    public DatasetQuery() {
    }

    protected boolean canEqual(Object other) {
        return other instanceof DatasetQuery;
    }

}
