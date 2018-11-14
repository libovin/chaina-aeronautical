package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Data
@Document
@ApiModel(value = "会议")
@XmlRootElement
public class Conference extends Base {

    @NotBlank(message = "会议名不能为空")
    @ApiModelProperty(example = "2018年xxx会议", value = "会议名")
    private String name;

    @ApiModelProperty(value = "次要会名")
    private String secondaryName;

    @ApiModelProperty(example = "会议简称", value = "会议简称")
    private String shortName;

    @ApiModelProperty(value = "系列会议名")
    private String seriesName;

    @ApiModelProperty(value = "会议周期")
    private String meetingCycle;

    @ApiModelProperty(value = "会议届次")
    private String meetingSession;

    @ApiModelProperty(value = "会议主题")
    private String meetingTheme;

    @ApiModelProperty(value = "主办单位")
    private String organizer;

    @ApiModelProperty(value = "协办单位")
    private String coOrganizer;

    @ApiModelProperty(value = "会议地址")
    private String venue;

    @ApiModelProperty(value = "专业分类")
    private String specialClassified;

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError = false;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map<String, Boolean> errorTag;

}
