package com.hiekn.china.aeronautical.knowledge.bean.define;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "kg_db_name")
public class KgDbName {
    @Id
    private String id;

    @Field("kg_name")
    private String kgName;

    @Field("db_name")
    private String dbName;
}
