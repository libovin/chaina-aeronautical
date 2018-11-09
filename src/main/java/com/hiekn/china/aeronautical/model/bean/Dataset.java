package com.hiekn.china.aeronautical.model.bean;

import com.hiekn.china.aeronautical.model.base.Base;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Dataset extends Base {
    @Indexed(unique = true)
    private String key;

    private String name;

    private List<DatasetTable> datasetTable;

    @Indexed
    private String owner;

    private String org;

}
