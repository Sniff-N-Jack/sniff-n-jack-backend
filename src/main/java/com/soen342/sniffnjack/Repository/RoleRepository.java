package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface RoleRepository extends MongoRepository<Role, Long> {
    Role findByName(String name);
    Collection<Role> findAllByNameIsIn(Collection<String> names);
}
