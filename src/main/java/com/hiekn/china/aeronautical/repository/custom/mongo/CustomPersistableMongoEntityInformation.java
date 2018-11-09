package com.hiekn.china.aeronautical.repository.custom.mongo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@RequiredArgsConstructor
class CustomPersistableMongoEntityInformation<T, ID extends Serializable> implements MongoWithoutCollectionEntityInformation<T, ID>  {

    private final @NonNull
    CustomMongoEntityInformation<T, ID> delegate;

    @Override
    public String getIdAttribute() {
        return delegate.getIdAttribute();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isNew(T t) {
        if (t instanceof Persistable) {
            return ((Persistable<ID>) t).isNew();
        }
        return delegate.isNew(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ID getId(T t) {
        if (t instanceof Persistable) {
            return (ID) ((Persistable<ID>) t).getId();
        }
        return delegate.getId(t);
    }

    @Override
    public Class<ID> getIdType() {
        return delegate.getIdType();
    }

    @Override
    public Class<T> getJavaType() {
        return delegate.getJavaType();
    }
}
