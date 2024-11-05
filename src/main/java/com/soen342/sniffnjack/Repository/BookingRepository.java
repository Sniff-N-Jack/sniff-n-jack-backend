package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByOfferingId(Long offeringId);
    List<Booking> findAllByClientId(Long clientId);
}
