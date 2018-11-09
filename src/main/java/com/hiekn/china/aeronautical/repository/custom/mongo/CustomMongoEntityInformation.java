package com.hiekn.china.aeronautical.repository.custom.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

import java.io.Serializable;

public class CustomMongoEntityInformation<T, ID extends Serializable> extends PersistentEntityInformation<T, ID>
        implements MongoWithoutCollectionEntityInformation<T, ID> {

    private final MongoPersistentEntity<T> entityMetadata;
    private final Class<ID> fallbackIdType;

    public CustomMongoEntityInformation(MongoPersistentEntity<T> entity) {
        this(entity, null);
    }

    @SuppressWarnings("unchecked")
    public CustomMongoEntityInformation(MongoPersistentEntity<T> entity, Class<ID> idType) {
        super(entity);
        this.entityMetadata = entity;
        this.fallbackIdType = idType != null ? idType : (Class<ID>) ObjectId.class;
    }

    public String getIdAttribute() {
        return entityMetadata.getIdProperty().getName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ID> getIdType() {

        if (this.entityMetadata.hasIdProperty()) {
            return super.getIdType();
        }

        return fallbackIdType != null ? fallbackIdType : (Class<ID>) ObjectId.class;
    }
}
