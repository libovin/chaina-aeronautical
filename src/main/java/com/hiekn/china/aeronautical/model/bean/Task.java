package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Document
@ApiModel("任务")
public class Task extends Base {

    @ApiModelProperty(example = "conference", value = "数据集类型")
    @NotBlank(message = "数据集类型不能为空")
    @Pattern(regexp = "(conference|institution|periodical|publisher|website)",
            message = "数据集类型必须为conference|institution|periodical|publisher|website")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    private String key;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("规则")
    private List<Rule> taskRule;

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
