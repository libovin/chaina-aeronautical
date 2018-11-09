package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 会议
 *
 *
 *
 */
@Data
@Document
@ApiModel(value = "会议\n")
public class Conference extends Base {

    @NotBlank(message = "会议标题不能为空")
    @ApiModelProperty(example = "2018年xxx会议", value = "会议名称")
    private String title;

    @ApiModelProperty(example = "www.baidu.com", value = "网站url")
    private String url;

    @ApiModelProperty(example = "yyy会议", value = "简称")
    private String shortName;

    @ApiModelProperty(example = "2018年zzz会议", value = "其他名称")
    private String otherName;

}
