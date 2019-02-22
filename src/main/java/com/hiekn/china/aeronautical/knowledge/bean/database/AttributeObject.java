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

    @Field(value = "entity_id", order = 2)
    private Long entityId;
    @Field(value = "entity_type", order = 3)
    private Long entityType;

    @Field(value = "attr_id", order = 4)
    private Integer attrId;
    @Field(value = "attr_value_type", order = 5)
    private String attrValueType;
    @Field(value = "attr_value", order = 6)
    private Long attrValue;

    @Field(value = "meta_data", order = 7)
    private Map metaData;
}
