package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, Long> {
    Role findByName(String name);
    @Query("{ 'privileges' : ?0 }")
    List<Role> findByPrivilege(String privilege);
}
