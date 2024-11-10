package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    T findByEmail(String email);
    boolean existsByEmail(String email);
    T getUserById(Long id);
}
