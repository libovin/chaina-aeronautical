package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 出版机构
 */
@Data
@Document
@XmlRootElement
public class Publisher extends Base {

    @ApiModelProperty(value = "出版物单位名称")
    private String name;

    @ApiModelProperty(value = "出版单位前缀")
    private String namePrefix;

    @ApiModelProperty(value = "出版社代号")
    private String code;

    @ApiModelProperty(value = "社长")
    private String chiefPublisher;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "网址")
    private String website;

    @ApiModelProperty(value = "地区")
    private String region;

    @ApiModelProperty(value = "主管单位")
    private String authorityOrganization;

    @ApiModelProperty(value = "主办单位")
    private String organizer;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError = false;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map<String, Boolean> errorTag;

}
