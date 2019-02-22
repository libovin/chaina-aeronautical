package com.hiekn.china.aeronautical.knowledge.bean.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "concept_instance")
public class ConceptInstance {

    @Id
    private String id;

    @Field("concept_id")
    private Long conceptId;

    @Field("ins_id")
    private Long insId;

    @Field("meta_data")
    private Map metaData;
}
