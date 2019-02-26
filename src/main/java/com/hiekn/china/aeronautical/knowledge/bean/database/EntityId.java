package com.hiekn.china.aeronautical.knowledge.bean.database;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "entity_id")
public class EntityId {

    @Id
    private String objId;

    private Long id;

    private String name;

    private Integer type;

    @Field(value = "concept_id")
    private Long conceptId;

}
