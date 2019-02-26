package com.hiekn.china.aeronautical.knowledge.bean.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "basic_info")
public class BasicInfo {

    @Id
    private Long id;

    private String name;

    private Integer type;

    @Field("meaning_tag")
    private String meaningTag;

    @Field("meta_data")
    private Map metaData;
}
