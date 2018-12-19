package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ApiModel
@XmlRootElement
public class WordMarkError {

    @ApiModelProperty(example = "name",value = "字段名", required = true)
    @NotBlank(message = "字段不能为空")
    private String column;

    @ApiModelProperty(example = "id1,id2,id3", value = "ids", required = true)
    @NotBlank(message = "ids不能为空")
    private String ids;
}
