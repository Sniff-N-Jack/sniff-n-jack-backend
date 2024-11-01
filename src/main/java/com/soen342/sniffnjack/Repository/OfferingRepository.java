package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Offering;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface OfferingRepository extends CrudRepository<Offering, Long> {
    List<Offering> findAllByInstructorId(Long instructorId);
    List<Offering> findAllByInstructorIsNull();
    List<Offering> findAllByInstructorIsNotNull();
}
