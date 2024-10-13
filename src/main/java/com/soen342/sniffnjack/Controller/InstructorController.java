package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.InstructorsRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Utils.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    @Autowired
    private InstructorsRepository instructorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/all")
    public Iterable<User> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @GetMapping("/get")
    public Instructor findInstructorByEmail(@RequestParam String email) throws UserNotFoundException {
        Instructor user = instructorRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }

        return user;
    }

    @GetMapping("/firstName")
    public Iterable<Instructor> findUsersByFirstName(@RequestParam String firstName) {
        return instructorRepository.findAllByFirstName(firstName);
    }

    @GetMapping("/lastName")
    public Iterable<Instructor> findUsersByLastName(@RequestParam String lastName) {
        return instructorRepository.findAllByLastName(lastName);
    }

    @GetMapping("/fullNameStrict")
    public Iterable<Instructor> findUsersByFullNameStrict(@RequestParam String firstName, @RequestParam String lastName) {
        return instructorRepository.findDistinctByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/fullNameLoose")
    public Iterable<Instructor> findUsersByFullNameLoose(@RequestParam String firstName, @RequestParam String lastName) {
        return instructorRepository.findDistinctByFirstNameOrLastName(firstName, lastName);
    }

    @GetMapping("/specialization")
    public Iterable<Instructor> findInstructorsBySpecialization(@RequestParam String specialization) {
        return instructorRepository.findAllByActivity(specialization);
    }

    @GetMapping("/availability")
    public Iterable<Instructor> findInstructorsByAvailability(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        Timeslot availability = new Timeslot(startTime, endTime);
        return instructorRepository.findAllByAvailability(availability);
    }

    @GetMapping("/availabilityAndSpecialization")
    public Iterable<Instructor> findInstructorsByAvailabilityAndSpecialization(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam String specialization) {
        Timeslot availability = new Timeslot(startTime, endTime);
        return instructorRepository.findAllByAvailabilityAndActivity(availability, specialization);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Instructor addInstructor(@RequestBody Instructor user) throws UserAlreadyExistsException {
        if (instructorRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("INSTRUCTOR"));
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        if (user.getSpecializations() != null) {
            user.setSpecializations(user.getSpecializations().stream().map(activityRepository::findByName).collect(Collectors.toList()));
        }
        if (user.getAvailabilities() != null) {
            user.setAvailabilities(user.getAvailabilities().stream().toList());

        }
        return instructorRepository.save(user);
    }

    @PatchMapping("/updatePersonal")
    public void updatePersonal(@RequestBody Instructor instructor) {
        Instructor currentInstructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        currentInstructor.setFirstName(instructor.getFirstName());
        currentInstructor.setLastName(instructor.getLastName());
        instructorRepository.save(currentInstructor);
    }

    @PatchMapping("/setAvailabilities")
    public void setAvailabilities(@RequestBody List<Timeslot> availabilities) {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setAvailabilities(availabilities);
        instructorRepository.save(instructor);
    }

    @PatchMapping("/setSpecializations")
    public void setSpecializations(@RequestBody List<String> specializations) {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setSpecializations(specializations.stream().map(activityRepository::findByName).collect(Collectors.toList()));
        instructorRepository.save(instructor);
    }
}
