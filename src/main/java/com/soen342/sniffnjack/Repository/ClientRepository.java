package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByEmail(String email);
    List<Client> findAllByFirstName(String firstName);
    List<Client> findAllByLastName(String lastName);
    List<Client> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    List<Client> findDistinctByFirstNameOrLastName(String firstName, String lastName);
    boolean existsByEmail(String email);


    @Query("select c from Client c where c.parent = ?1")
    List<Client> findDistinctByParent(Client parent);
}
