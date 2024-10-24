package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity, Long> {
    Activity findByName(String name);
    List<Activity> findDistinctByNameIn(List<String> name);
    void deleteByName(String name);
    boolean existsByName(String name);
}
