package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Booking;
import com.soen342.sniffnjack.Entity.Client;
import com.soen342.sniffnjack.Entity.Offering;
import com.soen342.sniffnjack.Exceptions.*;
import com.soen342.sniffnjack.Repository.BookingRepository;
import com.soen342.sniffnjack.Repository.ClientRepository;
import com.soen342.sniffnjack.Repository.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@Transactional
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
    public Booking addBooking(@RequestParam Long offeringId, @RequestParam Long clientID) throws
            InvalidOfferingException, UserNotFoundException, OfferingFullException, ClientAlreadyBookedOfferingException, BookingRequiresParentException, BookingForOtherUserException, OverlappingLessonsException {
        Client client = clientRepository.findById(clientID).orElse(null);
        if (client == null) {
            throw new UserNotFoundException(clientID);
        }

        // Check if client is a minor
        if (client.isMinor()) {
            Client loggedInClient = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (client == loggedInClient) {
                throw new BookingRequiresParentException();
            }
            if (loggedInClient != client.getParent()) {
                throw new BookingForOtherUserException();
            }
        }

        Offering offering = offeringRepository.findById(offeringId).orElse(null);
        if (offering == null) {
            throw new InvalidOfferingException();
        }

        // Check if offering is full
        if (offering.isFull()) {
            throw new OfferingFullException();
        }

        // Check if client has already booked this offering
        if (bookingRepository.findAllByClientId(clientID).stream().anyMatch(booking -> booking.getOffering().getId().equals(offeringId))) {
            throw new ClientAlreadyBookedOfferingException();
        }

        // Check if client already has a booking at this time
        if (bookingRepository.findAllByClientId(clientID).stream().anyMatch(booking -> booking.getOffering().getLesson().isOverlapping(offering.getLesson()))) {
            throw new OverlappingLessonsException();
        }

        Booking booking = new Booking(offering, client);
        return bookingRepository.save(booking);
    }

    @DeleteMapping("/delete")
    public void deleteBooking(@RequestParam Long id) throws CustomBadRequestException {
        if (!bookingRepository.existsById(id)) {
            throw new CustomBadRequestException("Invalid booking ID");
        }
        try {
            bookingRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomBadRequestException("Booking is referenced by other entities");
        }
    }
}
