package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Client;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClientRepository extends UserRepository<Client> {
    @Query("{ 'parent' : ?0 }")
    List<Client> findDistinctByParent(Long parentId);
}
