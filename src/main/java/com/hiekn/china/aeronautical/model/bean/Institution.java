package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 研究机构
 */
@Data
@Document
@XmlRootElement
public class Institution extends Base {

    @ApiModelProperty(value = "研究机构名称")
    private String name;

    @ApiModelProperty(value = "机构简称")
    private String shortName;

    @ApiModelProperty(example = "翻译名称", value = "翻译名称")
    private String translationName;

    @ApiModelProperty(value = "主要领导")
    private String mainLeaders;

    @ApiModelProperty(value = "首席专家")
    private String chiefExpert;

    @ApiModelProperty(value = "主管单位")
    private String authorityOrganization;

    @ApiModelProperty(value = "机构类型")
    private String organizationType;

    @ApiModelProperty(value = "机构历史信息")
    private String institutionalHistory;

    @ApiModelProperty(value = "主要业务")
    private String primaryService;

    @ApiModelProperty(value = "组织结构")
    private String organizationStructure;

    @ApiModelProperty(value = "员工")
    private String employee;

    @ApiModelProperty(value = "财务状况")
    private String financialSituation;

    @ApiModelProperty(value = "研究领域")
    private String researchField;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "重大项目")
    private String majorProjects;

    @ApiModelProperty(value = "举办活动")
    private String events;

    @ApiModelProperty(value = "成立时间")
    private String foundingTime;

    @ApiModelProperty(value = "国别")
    private String country;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "简介")
    private String introductions;

    @ApiModelProperty(value = "网站主页")
    private String homepage;

    @ApiModelProperty(value = "出版物")
    private String publication;

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError = false;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map<String, Boolean> errorTag;
}
