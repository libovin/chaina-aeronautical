package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 期刊
 */
@Data
@Document
public class Periodical extends Base {

    /**
     * 标题
     */
    private String title;

    /**
     * URL
     */
    private String url;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 其他名称
     */
    private String otherName;

    /**
     * 作者
     */
    private String author;

    /**
     * 发表日期
     */
    private String publishTime;

}
