package com.hiekn.china.aeronautical.knowledge.bean.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "parent_son")
public class ParentSon {
    @Id
    private String id;

    private Long parent;

    private Long son;

    @Field("meta_data")
    private Map metaData;
}
