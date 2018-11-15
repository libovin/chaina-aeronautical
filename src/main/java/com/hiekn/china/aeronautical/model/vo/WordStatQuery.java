package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class WordStatQuery extends PageQuery {

    @ApiModelProperty(example = "name",value = "字段名", required = true)
    @NotBlank(message = "字段不能为空")
    private String column;

    @ApiModelProperty(example = "1",value = "最小次数", required = true)
    @NotNull(message = "最小次数不能为空")
    private Integer min;

    @ApiModelProperty(example = "5",value = "最大次数", required = true)
    @NotNull(message = "最大次数不能为空")
    private Integer max;

}
