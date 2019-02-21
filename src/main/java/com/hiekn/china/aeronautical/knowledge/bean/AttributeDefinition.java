package com.hiekn.china.aeronautical.knowledge.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class AttributeDefinition {
    @Id
    private String id;

    private String name;

    private String type;

    @Field("domain_value")
    private Long domainValue;

    @Field("data_type")
    private Integer dataType;

    @Field("data_unit")
    private String dataUnit;

    @Field("is_functional")
    private Integer functional;

    private String creator;

    @Field("create_time")
    private String createTime;

    private String modifier;

    @Field("modify_time")
    private String modifyTime;

    @Field("id")
    private Integer beanId;

    @Field("range_value")
    private String rangeValue;

}
