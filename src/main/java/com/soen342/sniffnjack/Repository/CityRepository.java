package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
