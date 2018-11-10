package com.hiekn.china.aeronautical.model.vo;

import com.hiekn.china.aeronautical.model.base.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ApiModel
public class ConferenceQuery extends PageQuery {

    /**
     * 标题
     */
    @ApiModelProperty(example = "会议名称", value = "会议名称",dataType = "Object")
    private Object title;

    /**
     * URL
     */
    private Object url;

    /**
     * 简称
     */
    private Object shortName;

    /**
     * 其他名称
     */
    private Object otherName;

    public ConferenceQuery() {
    }


    protected boolean canEqual(Object other) {
        return other instanceof ConferenceQuery;
    }

}
