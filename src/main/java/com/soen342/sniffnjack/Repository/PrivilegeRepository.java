package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Privilege;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PrivilegeRepository extends MongoRepository<Privilege, UUID> {
    Privilege findByName(String name);
}
