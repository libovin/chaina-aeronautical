package com.hiekn.china.aeronautical.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Data
public abstract class Base {
    @Id
    @ApiModelProperty(value = "id",hidden = true)
    private String id;

    @Version
    @ApiModelProperty(hidden = true)
    private Long version;

    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Long createTime;

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Long updateTime;

}
