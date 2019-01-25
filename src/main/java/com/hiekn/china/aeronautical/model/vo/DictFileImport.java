package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Data
@ApiModel
public class DictFileImport extends FileImport {

    @ApiModelProperty(example = "conference", value = "数据集类型")
    @FormDataParam("table")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key")
    @FormDataParam("key")
    private String key;

    @ApiModelProperty(example = "name", value = "列")
    @FormDataParam("column")
    private String column;

    @ApiModelProperty(example = "会议名称字典", value = "表类型")
    @FormDataParam("name")
    private String name;
}
