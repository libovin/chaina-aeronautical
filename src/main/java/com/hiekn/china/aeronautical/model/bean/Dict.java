package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedHashSet;

/**
 * 字典
 */
@Data
@Document
@XmlRootElement
public class Dict extends Base {

    @NotBlank(message = "数据集类型不能为空")
    @Pattern(regexp = "(conference|institution|periodical|publisher|website)",
            message = "数据集类型必须为conference|institution|periodical|publisher|website")
    @ApiModelProperty(example = "conference", value = "数据集类型")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    private String key;

    @ApiModelProperty(example = "name", value = "列")
    private String column;

    @ApiModelProperty(example = "会议名称字典", value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典内容")
    private LinkedHashSet<String> text;
}
