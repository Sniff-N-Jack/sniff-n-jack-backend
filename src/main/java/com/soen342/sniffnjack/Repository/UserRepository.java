package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface UserRepository<T extends User> extends CrudRepository<User, Long> {
    T findByEmail(String email);
    List<T> findAllByFirstName(String firstName);
    List<T> findAllByLastName(String lastName);
    List<T> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    List<T> findDistinctByFirstNameOrLastName(String firstName, String lastName);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
