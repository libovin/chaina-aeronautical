package com.hiekn.china.aeronautical.knowledge.bean.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "attribute_string")
public class AttributeString {
    @Id
    private String id;

    @Field("entity_id")
    private Long entityId;

    @Field("attr_id")
    private Integer attrId;

    @Field("entity_type")
    private Long entityType;

    @Field("attr_value")
    private String attrValue;

    @Field("meta_data")
    private Map metaData;

    public AttributeString() {
    }

    public AttributeString(Long entityId, Integer attrId, Long entityType, String attrValue, Map metaData) {
        this.entityId = entityId;
        this.attrId = attrId;
        this.entityType = entityType;
        this.attrValue = attrValue;
        this.metaData = metaData;
    }
}
