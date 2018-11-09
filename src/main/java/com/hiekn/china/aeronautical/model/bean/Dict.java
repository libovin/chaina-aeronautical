package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 字典
 */
@Data
@Document
public class Dict extends Base {
    /**
     * 表类型
     */
    private String table;

    /**
     * 字段类型
     */
    private String column;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 文本
     */
    private String text;
}
