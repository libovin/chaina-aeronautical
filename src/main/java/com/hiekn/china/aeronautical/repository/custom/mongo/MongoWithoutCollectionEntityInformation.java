package com.hiekn.china.aeronautical.repository.custom.mongo;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

public interface MongoWithoutCollectionEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {
    /**
     * Returns the attribute that the id will be persisted to.
     *
     * @return
     */
    String getIdAttribute();
}
