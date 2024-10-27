package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByEmail(String email);
    List<Admin> findAllByFirstName(String firstName);
    List<Admin> findAllByLastName(String lastName);
    List<Admin> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    List<Admin> findDistinctByFirstNameOrLastName(String firstName, String lastName);
    boolean existsByEmail(String email);
}
