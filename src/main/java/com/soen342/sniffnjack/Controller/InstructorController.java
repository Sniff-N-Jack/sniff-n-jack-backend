package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Configuration.BasicAuthSecurity;
import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidCityNameException;
import com.soen342.sniffnjack.Exceptions.UserAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;
import com.soen342.sniffnjack.Repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/instructors")
@Transactional
public class InstructorController {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository<User> userRepository;

    @GetMapping("/all")
    public Iterable<Instructor> getAllInstructors() {
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

//    @GetMapping("/getBySpecialization")
//    public Iterable<Instructor> findInstructorsBySpecialization(@RequestParam String activityName) throws InvalidActivityNameException {
//        Activity specialization = activityRepository.findByName(activityName);
//        if (specialization == null) {
//            throw new InvalidActivityNameException(activityName);
//        }
//        return instructorRepository.findDistinctBySpecializationsContaining(specialization);
//    }
//
//    @GetMapping("/getByAvailability")
//    public Iterable<Instructor> findInstructorsByAvailability(@RequestParam String cityName) throws InvalidCityNameException {
//        City availability = cityRepository.findByName(cityName);
//        if (availability == null) {
//            throw new InvalidCityNameException(cityName);
//        }
//        return instructorRepository.findDistinctByAvailabilitiesContaining(availability);
//    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Instructor addInstructor(@RequestBody Instructor user) throws UserAlreadyExistsException, InvalidCityNameException, InvalidActivityNameException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("INSTRUCTOR"));
        user.setPassword(BasicAuthSecurity.passwordEncoder().encode(user.getPassword()));
        if (user.getSpecializations() != null) {
            user.setSpecializations(
                    getActivityList(user.getSpecializations().stream().map(Activity::getName).collect(Collectors.toCollection(ArrayList::new))));
        }
        if (user.getAvailabilities() != null) {
            user.setAvailabilities(
                    getCityList(user.getAvailabilities().stream().map(City::getName).collect(Collectors.toCollection(ArrayList::new))));
        }
        return instructorRepository.save(user);
    }

    @PatchMapping(value = "/updatePersonal", consumes = "application/json")
    public void updatePersonal(@RequestBody Instructor instructor) {
        Instructor currentInstructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        currentInstructor.setFirstName(instructor.getFirstName());
        currentInstructor.setLastName(instructor.getLastName());
        currentInstructor.setPhone(instructor.getPhone());
        instructorRepository.save(currentInstructor);
    }

    @PatchMapping(value = "/setAvailabilities")
    public Instructor setAvailabilities(@RequestParam ArrayList<String> names) throws InvalidCityNameException {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setAvailabilities(getCityList(names));
        return instructorRepository.save(instructor);
    }

    @PatchMapping(value = "/setSpecializations")
    public Instructor setSpecializations(@RequestParam ArrayList<String> names) throws InvalidActivityNameException {
        Instructor instructor = instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setSpecializations(getActivityList(names));
        return instructorRepository.save(instructor);
    }

    private ArrayList<City> getCityList(ArrayList<String> uncheckedCityList) throws InvalidCityNameException {
        ArrayList<City> cityList = uncheckedCityList.stream()
                .map(name -> cityRepository.findByName(name)).filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
        // If any city is not found, throw an exception
        ArrayList<String> invalidCityNames = uncheckedCityList.stream()
                .filter(name -> cityList.stream().noneMatch(city -> city.getName().equals(name)))
                .collect(Collectors.toCollection(ArrayList::new));
        if (!invalidCityNames.isEmpty()) {
            throw new InvalidCityNameException(invalidCityNames);
        }
        return cityList;
    }

    private ArrayList<Activity> getActivityList(ArrayList<String> uncheckedActivityList) throws InvalidActivityNameException {
        ArrayList<Activity> activityList = uncheckedActivityList.stream()
                .map(name -> activityRepository.findByName(name)).filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
        // If any activity is not found, throw an exception
        ArrayList<String> invalidActivityNames = uncheckedActivityList.stream()
                .filter(name -> activityList.stream().noneMatch(activity -> activity.getName().equals(name)))
                .collect(Collectors.toCollection(ArrayList::new));
        if (!invalidActivityNames.isEmpty()) {
            throw new InvalidActivityNameException(invalidActivityNames);
        }
        return activityList;
    }
}
