package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ApiModel
public class ConferenceQuery extends PageQuery {

    /**
     * 会议名称、会议简称、会议届次、主办单位、专业分类
     *
     */

    @ApiModelProperty(example = "会议名称", value = "会议名称",dataType = "Object")
    private String name;

    @ApiModelProperty(example = "会议简称", value = "会议简称")
    private String shortName;

    @ApiModelProperty(value = "会议届次")
    private String meetingSession;

    @ApiModelProperty(value = "专业分类")
    private String specialClassified;

}
