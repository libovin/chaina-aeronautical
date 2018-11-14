package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Document
@XmlRootElement
public class Dataset extends Base {

    @ApiModelProperty(example = "conference", value = "数据集类型")
    @Indexed
    @NotBlank(message = "数据集类型不能为空")
    @Pattern(regexp = "(conference|institution|periodical|publisher|website)",
            message = "数据集类型必须为conference|institution|periodical|publisher|website")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    @Indexed
    private String key;

    @ApiModelProperty(hidden = true)
    @Indexed(unique = true)
    private String typeKey;

    @ApiModelProperty(example = "期刊数据集", value = "数据集名称")
    private String name;

}
