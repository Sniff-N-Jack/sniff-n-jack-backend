package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository<T extends User> extends MongoRepository<User, Long> {
    T findByEmail(String email);
    List<T> findAllByFirstName(String firstName);
    List<T> findAllByLastName(String lastName);
    List<T> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    List<T> findDistinctByFirstNameOrLastName(String firstName, String lastName);
    List<T> findByRole(String role);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
