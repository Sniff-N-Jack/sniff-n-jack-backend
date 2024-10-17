package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Privilege;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege, Long> {
    Privilege findByName(String name);
}
