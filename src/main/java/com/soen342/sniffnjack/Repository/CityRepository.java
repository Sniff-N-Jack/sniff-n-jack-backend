package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
