package com.hiekn.china.aeronautical.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;
import java.io.InputStream;

@Data
@ApiModel("数据集")
public class DatasetFile {

    @ApiModelProperty(example = "conference", value = "数据集类型",required = true)
    @NotBlank(message = "数据集类型不能为空")
    @Pattern(regexp = "(conference|institution|periodical|publisher|website)",
            message = "数据集类型必须为conference|institution|periodical|publisher|website")
    @DefaultValue("publisher")
    @FormDataParam("table")
    private String table;

    @ApiModelProperty(example = "default", value = "数据集key", required = true)
    @DefaultValue("default")
    @FormDataParam("key")
    private String key;

    @ApiModelProperty(example = "期刊数据集", value = "数据集名称")
    @FormDataParam("name")
    private String name;


    @ApiParam(value = "file")
    @FormDataParam("file")
    private FormDataContentDisposition fileInfo;

    @ApiParam(value = "file")
    @FormDataParam("file")
    private FormDataBodyPart formDataBodyPart;

    @ApiParam(value = "file")
    @FormDataParam("file")
    private InputStream fileIn;

}
