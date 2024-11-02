package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Booking;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.Offering;
import com.soen342.sniffnjack.Exceptions.InvalidOfferingException;
import com.soen342.sniffnjack.Repository.BookingRepository;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OfferingRepository offeringRepository;

    @GetMapping("/all")
    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/get")
    public Booking getBookingById(@RequestParam Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @GetMapping("/getByClient")
    public Iterable<Booking> getBookingsByClient(@RequestParam Long clientId) {
        return bookingRepository.findAllByClientId(clientId);
    }

    @GetMapping("/getByOffering")
    public Iterable<Booking> getBookingsByOffering(@RequestParam Long offeringId) {
        return bookingRepository.findAllByOfferingId(offeringId);
    }

    @PostMapping("/add")
    public Booking addBooking(@RequestParam Long offeringId) throws InvalidOfferingException {
        Client client = (Client) clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Offering offering = offeringRepository.findById(offeringId).orElse(null);
        if (offering == null) {
            throw new InvalidOfferingException();
        }
        Booking booking = new Booking(offering, client);
        return bookingRepository.save(booking);
    }

    @DeleteMapping("/delete")
    public void deleteBooking(@RequestParam Long id) {
        bookingRepository.deleteById(id);
    }
}
