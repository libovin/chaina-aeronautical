package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 期刊
 */
@Data
@Document
@ApiModel(value = "期刊")
@XmlRootElement
public class Periodical extends Base {

    @NotBlank(message = "期刊名不能为空")
    @ApiModelProperty(example = "期刊名称", value = "期刊名称")
    private String name;

    @ApiModelProperty(example = "翻译名称", value = "翻译名称")
    private String translationName;

    @ApiModelProperty(value = "主管单位")
    private String authorityOrganization;

    @ApiModelProperty(example = "主办单位", value = "主办单位")
    private String organizer;

    @ApiModelProperty(value = "中图刊号")
    private String clc;

    @ApiModelProperty(example = "语种", value = "语种")
    private String language;

    @ApiModelProperty(example = "国别", value = "国别")
    private String country;

    @ApiModelProperty(value = "出版商")
    private String publisher;

    @ApiModelProperty(value = "出版周期")
    private String publishCycle;

    @ApiModelProperty(value = "出版地")
    private String publishPlace;

    @ApiModelProperty(example = "1.0", value = "影响因子")
    private Object impactFactor;

    @ApiModelProperty(value = "分类信息")
    private String classifiedInfo;

    @ApiModelProperty(value = "主编")
    private String chiefEditor;

    @ApiModelProperty(value = "开本")
    private String formatSize;

    @ApiModelProperty(value = "创刊时间")
    private String startupTime;

    @ApiModelProperty(value = "国际标准刊号")
    private String issn;

    @ApiModelProperty(value = "国内统一刊号")
    private String cssn;

    @ApiModelProperty(value = "订价价格")
    private Object price;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "历史沿革")
    private Object history;

    @ApiModelProperty(value = "封面截图")
    private String screenshot;

    @ApiModelProperty(value = "相关网址")
    private String website;

    @ApiModelProperty(value = "收录情况")
    private String inclusionStatus;

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError = false;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map<String, Boolean> errorTag;
}
