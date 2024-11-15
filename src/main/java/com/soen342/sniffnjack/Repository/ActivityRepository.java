package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Transactional
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByName(String name);
    List<Activity> findDistinctByNameIn(List<String> name);
    void deleteByName(String name);
    boolean existsByName(String name);
}
