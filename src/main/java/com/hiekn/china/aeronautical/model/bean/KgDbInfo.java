package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ApiModel(value = "图谱信息")
@Document
@XmlRootElement
public class KgDbInfo extends Base {

    @ApiModelProperty(value = "图谱名称")
    @NotBlank(message = "图谱名称不能为空")
    private String name;

    @ApiModelProperty(value = "KgName")
    @NotBlank(message = "kgName 不能为空")
    private String kgName;

}
