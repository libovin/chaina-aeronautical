package com.hiekn.china.aeronautical.repository.custom.mongo;

import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;

final class CustomMongoEntityInformationSupport {
    private CustomMongoEntityInformationSupport() {}

    static <T, ID extends Serializable> CustomMongoEntityInformation<T, ID> entityInformationFor(MongoPersistentEntity<?> entity, Class<?> idType) {
        Assert.notNull(entity, "Entity must not be null!");
        CustomMongoEntityInformation<T, ID> entityInformation = new CustomMongoEntityInformation<T, ID>((MongoPersistentEntity<T>) entity, (Class<ID>) idType);
        return ClassUtils.isAssignable(Persistable.class, entity.getType()) ? new CustomPersistableMongoEntityInformation<T, ID>(entityInformation) : entityInformation;
    }
}
