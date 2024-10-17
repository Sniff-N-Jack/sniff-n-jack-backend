package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, Long> {
    Activity findByName(String name);
}
