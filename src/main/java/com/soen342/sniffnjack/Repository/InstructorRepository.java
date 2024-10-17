package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Utils.Timeslot;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends UserRepository<Instructor> {
    @Query("select i from Instructor i  where ?1 in i.specializations")
    Iterable<Instructor> findAllBySpecialization(String activity);
    @Query("select i from Instructor i where ?1 in i.availabilities") // TODO: Change to see if i.availabilities contains ?1
    Iterable<Instructor> findAllByAvailability(Timeslot availability);
    @Query("select i from Instructor i where ?1 in i.availabilities and ?2 in i.specializations")
    Iterable<Instructor> findAllByAvailabilityAndActivity(Timeslot availability, String activity);
}
