package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ClientRepository extends UserRepository<Client> {
    @Query("select c from Client c where c.parent = ?1")
    List<Client> findAllByParent(Client parent);
}
