package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 标错记录
 */
@Data
@Document
public class MarkError extends Base {

    /**
     * 标记的表类型
     */
    private String table;

    /**
     * 标记的字段类型
     */
    private String column;

    /**
     * 错误文本
     */
    private String errorText;

    /**
     * 正确文本
     */
    private String correctText;

    /**
     * 是否作用
     */
    private Boolean active = true;


}
