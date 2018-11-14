package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 网站
 */
@Data
@Document
@XmlRootElement
public class Website extends Base {

    @ApiModelProperty(value = "网址")
    private String url;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "翻译名称")
    private String translationName;

    @ApiModelProperty(value = "网站类别")
    private String type;

    @ApiModelProperty(value = "涉及专业")
    private String involvedMajor;

    @ApiModelProperty(value = "更新频率")
    private String updateFrequency;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "服务器类型")
    private String serverType;

    @ApiModelProperty(value = "页面类型")
    private String pageType;

    @ApiModelProperty(value = "收录日期")
    private String embodyDate;

    @ApiModelProperty(value = "所属国家")
    private String country;

    @ApiModelProperty(value = "编码方式")
    private String encoded;

    @ApiModelProperty(value = "网站简介")
    private String introduction;

    @ApiModelProperty(value = "网站首页截图")
    private String screenshot;

    @ApiModelProperty(value = "重定向信息")
    private String redirection;

    @ApiModelProperty(value = "标错")
    private Map<String,Boolean> errorTag;

}
