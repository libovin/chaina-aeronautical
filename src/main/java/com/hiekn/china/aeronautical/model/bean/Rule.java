package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 规则
 */
@Data
@Document
public class Rule extends Base {

    /**
     * 规则名称
     */
    private String name;

    private String type;
}
