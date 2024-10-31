package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Offering;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface OfferingRepository extends CrudRepository<Offering, Long> {
    List<Offering> findAllByInstructorId(Long instructorId);
    List<Offering> findAllByLocationId(Long locationId);
    List<Offering> findAllByDayOfWeek(DayOfWeek dayOfWeek);
    List<Offering> findAllByInstructorIdAndLocationId(Long instructorId, Long locationId);
    List<Offering> findAllByInstructorIdAndDayOfWeek(Long instructorId, DayOfWeek dayOfWeek);
    List<Offering> findAllByLocationIdAndDayOfWeek(Long locationId, DayOfWeek dayOfWeek);
    List<Offering> findAllByInstructorIdAndLocationIdAndDayOfWeek(Long instructorId, Long locationId, DayOfWeek dayOfWeek);
}
