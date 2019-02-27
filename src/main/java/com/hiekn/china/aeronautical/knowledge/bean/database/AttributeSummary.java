package com.hiekn.china.aeronautical.knowledge.bean.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "attribute_summary")
public class AttributeSummary {

    @Id
    private String id;

    @Field("entity_id")
    private Long entityId;

    @Field("attr_id")
    private Integer attrId;

    public AttributeSummary() {
    }

    public AttributeSummary(Long entityId, Integer attrId) {
        this.entityId = entityId;
        this.attrId = attrId;
    }
}
