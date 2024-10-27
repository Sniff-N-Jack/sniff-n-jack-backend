package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Entity.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    Instructor findByEmail(String email);
    List<Instructor> findAllByFirstName(String firstName);
    List<Instructor> findAllByLastName(String lastName);
    List<Instructor> findDistinctByFirstNameAndLastName(String firstName, String lastName);
    List<Instructor> findDistinctByFirstNameOrLastName(String firstName, String lastName);
    boolean existsByEmail(String email);

    @Query("select i from Instructor i where i.specializations = :specialization")
    List<Instructor> findDistinctBySpecialization(Activity specialization);
    @Query("select i from Instructor i where i.availabilities = :availability")
    List<Instructor> findDistinctByAvailability(City availability);
}
