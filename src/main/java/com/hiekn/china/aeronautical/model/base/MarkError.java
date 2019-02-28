package com.hiekn.china.aeronautical.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
public class MarkError {
    @Id
    @ApiModelProperty(value = "id",hidden = true)
    private Long id;

    @ApiModelProperty(value = "是否有错", hidden = true)
    private Boolean hasError;

    @ApiModelProperty(value = "标错", hidden = true)
    private Map hasErrorTag;

}
