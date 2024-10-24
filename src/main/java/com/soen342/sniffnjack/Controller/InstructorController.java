package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import com.soen342.sniffnjack.Utils.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    @Autowired
    private InstructorRepository instructorRepository;

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
        return instructorRepository.findAllBySpecialization(activityRepository.findByName(specialization).getId());
    }

    @GetMapping("/availability")
    public Iterable<Instructor> findInstructorsByAvailability(@RequestBody Timeslot availability) {
        return instructorRepository.findAllByAvailability(availability);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Instructor addInstructor(@RequestBody Instructor user) throws UserAlreadyExistsException {
        if (instructorRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("INSTRUCTOR"));
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        if (user.getSpecializations() != null) {
            user.setSpecializations(activityRepository.findDistinctByNameIn(user.getSpecializations().stream().map(Activity::getName).toList()));
        }
        if (user.getAvailabilities() != null) {
            user.setAvailabilities(user.getAvailabilities().stream().toList());

        }
        return instructorRepository.save(user);
    }

    @PatchMapping(value = "/updatePersonal", consumes = "application/json")
    public void updatePersonal(@RequestBody Instructor instructor) {
        Instructor currentInstructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        currentInstructor.setFirstName(instructor.getFirstName());
        currentInstructor.setLastName(instructor.getLastName());
        instructorRepository.save(currentInstructor);
    }

    @PatchMapping(value = "/setAvailabilities", consumes = "application/json")
    public Instructor setAvailabilities(@RequestBody List<Timeslot> availabilities) throws IllegalArgumentException {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setAvailabilities(availabilities);
        return instructorRepository.save(instructor);
    }

    @PatchMapping(value = "/setSpecializations", consumes = "application/json")
    public Instructor setSpecializations(@RequestBody List<String> specializations) {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setSpecializations(specializations.stream().map(activityRepository::findByName).collect(Collectors.toList()));
        return instructorRepository.save(instructor);
    }
}
