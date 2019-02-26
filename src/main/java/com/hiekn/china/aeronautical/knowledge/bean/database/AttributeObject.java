package com.hiekn.china.aeronautical.knowledge.bean.database;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "attribute_object")
public class AttributeObject {
    @Id
    private String id;

    @Field(value = "entity_id")
    private Long entityId;

    @Field(value = "entity_type")
    private Long entityType;

    @Field(value = "attr_id")
    private Integer attrId;

    @Field(value = "attr_value_type")
    private String attrValueType;

    @Field(value = "attr_value")
    private Long attrValue;

    @Field(value = "meta_data")
    private Map metaData;
}
