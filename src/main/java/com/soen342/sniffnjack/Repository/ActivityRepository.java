package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    Activity findByName(String name);
}
