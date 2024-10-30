package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Entity.Instructor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorRepository extends UserRepository<Instructor> {
//    @Query("select i from Instructor i where ?1 in i.specializations")
//    List<Instructor> findDistinctBySpecializationsContaining(Activity specialization);
//    List<Instructor> findDistinctByAvailabilitiesContaining(City availability);
}
