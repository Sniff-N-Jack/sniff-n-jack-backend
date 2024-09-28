package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
    Collection<Role> findAllByNameIsIn(Collection<String> names);
}
