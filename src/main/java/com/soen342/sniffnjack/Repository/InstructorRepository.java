package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Utils.Timeslot;
import org.springframework.data.mongodb.repository.Query;

public interface InstructorRepository extends UserRepository<Instructor> {
    @Query("{ 'specializations' : ?0 }")
    Iterable<Instructor> findAllBySpecialization(Long activityId);
    @Query("{ 'availabilities' : ?0 }")
    Iterable<Instructor> findAllByAvailability(Timeslot availability);
}
