package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.Location;
import com.soen342.sniffnjack.Entity.Offering;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidLocationException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import com.soen342.sniffnjack.Repository.LocationRepository;
import com.soen342.sniffnjack.Repository.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offerings")
public class OfferingController {
    @Autowired
    private OfferingRepository offeringRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ActivityRepository activityRepository;

    private void checkOffering(Offering offering) throws InvalidActivityNameException, InvalidLocationException {
        if (offering.getActivity().getId() == null) {
            Activity activity = activityRepository.findByName(offering.getActivity().getName());
            if (activity == null) {
                throw new InvalidActivityNameException(offering.getActivity().getName());
            }
            offering.setActivity(activity);
        }

        if (offering.getLocation().getId() == null) {
            throw new InvalidLocationException();
        }
    }

    @GetMapping("/all")
    public Iterable<Offering> getAllOfferings() {
        return offeringRepository.findAll();
    }

    @GetMapping("/get")
    public Offering getOfferingById(@RequestParam Long id) {
        return offeringRepository.findById(id).orElse(null);
    }

    @GetMapping("/getByInstructor")
    public Iterable<Offering> getOfferingsByInstructor() {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return offeringRepository.findAllByInstructorId(instructor.getId());
    }

    @GetMapping("/getAvailable")
    public Iterable<Offering> getAvailableOfferings() {
        return offeringRepository.findAllByInstructorIsNull();
    }

    @GetMapping("/getTaken")
    public Iterable<Offering> getTakenOfferings() {
        return offeringRepository.findAllByInstructorIsNotNull();
    }

    @PostMapping("/add")
    public Offering addOffering(@RequestBody Offering offering) throws InvalidActivityNameException, InvalidLocationException {
        checkOffering(offering);
        return offeringRepository.save(offering);
    }

    @PatchMapping("/update")
    public Offering updateOffering(@RequestBody Offering offering) throws InvalidActivityNameException, InvalidLocationException {
        checkOffering(offering);
        return offeringRepository.save(offering);
    }

    @DeleteMapping("/delete")
    public void deleteOffering(@RequestParam Long id) {
        offeringRepository.deleteById(id);
    }
}
