package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findByOfferingId(Long offeringId);
    List<Booking> findByClientId(Long clientId);
}
