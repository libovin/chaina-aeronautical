package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Document
@ApiModel(value = "文献")
@XmlRootElement
public class Literature extends Base {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "书名")
    private String bookName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "出版机构")
    private String publisher;

    @ApiModelProperty(value = "出版年份")
    private String year;

    @ApiModelProperty(value = "ISBN")
    private String isbn;

    @ApiModelProperty(value = "相对路径")
    private String path;


}
