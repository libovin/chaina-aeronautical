package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.bean.RuleModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CheckRule {

    @ApiModelProperty(value = "待校验文本")
    @NotBlank(message = "校验文本不能为空")
    private String content;

    @Valid
    @ApiModelProperty(value = "规则")
    @Size(min = 1, message = "规则数量必须大于1")
    @NotNull(message = "校验规则不能为空")
    private List<RuleModel> rules;
}
