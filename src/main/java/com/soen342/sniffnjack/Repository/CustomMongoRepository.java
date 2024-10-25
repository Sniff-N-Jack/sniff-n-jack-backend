package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.UuidIdentifiedEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface CustomMongoRepository<T extends UuidIdentifiedEntity> extends MongoRepository<T, UUID> {
}
