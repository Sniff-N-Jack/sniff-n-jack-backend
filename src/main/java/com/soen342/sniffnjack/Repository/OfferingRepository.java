package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OfferingRepository extends JpaRepository<Offering, Long> {
    List<Offering> findAllByInstructorId(Long instructorId);
    List<Offering> findAllByInstructorIsNull();
    List<Offering> findAllByInstructorIsNotNull();
}
