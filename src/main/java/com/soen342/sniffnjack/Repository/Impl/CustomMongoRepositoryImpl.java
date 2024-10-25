package com.soen342.sniffnjack.Repository.Impl;

import com.soen342.sniffnjack.Repository.CustomMongoRepository;
import com.soen342.sniffnjack.Entity.UuidIdentifiedEntity;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.util.List;
import java.util.UUID;

public class CustomMongoRepositoryImpl<T extends UuidIdentifiedEntity> extends SimpleMongoRepository<T, UUID> implements CustomMongoRepository<T> {
    public CustomMongoRepositoryImpl(MongoEntityInformation<T, UUID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    protected <S extends T> boolean generateId(S entity) {
        if (entity != null && entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            return true;
        }
        return false;
    }

    @Override
    public <S extends T> S save(S entity) {
        return generateId(entity) ? super.insert(entity) : super.save(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::generateId);
        return super.saveAll(entities);
    }
}
