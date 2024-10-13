package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Utils.Timeslot;

import java.time.LocalDateTime;

public interface InstructorsRepository extends UserRepository<Instructor> {
    Iterable<Instructor> findAllByActivity(String activity);
    Iterable<Instructor> findAllByAvailability(Timeslot availability);
    Iterable<Instructor> findAllByAvailabilityAndActivity(Timeslot availability, String activity);
}
