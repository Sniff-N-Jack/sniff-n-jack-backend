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
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.CityRepository;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/all")
    public Iterable<User> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @GetMapping("/get")
    public Instructor findInstructorByEmail(@RequestParam String email) throws UserNotFoundException {
        Instructor user = (Instructor) instructorRepository.findByEmail(email);
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
        if (instructorRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setRole(roleRepository.findByName("INSTRUCTOR"));
        user.setPassword((user.getPassword()));
        if (user.getSpecializations() != null) {
            user.setSpecializations(getActivityList(user.getSpecializations()));
        }
        if (user.getAvailabilities() != null) {
            user.setAvailabilities(getCityList(user.getAvailabilities()));
        }
        return instructorRepository.save(user);
    }

    @PatchMapping(value = "/updatePersonal", consumes = "application/json")
    public void updatePersonal(@RequestBody Instructor instructor) {
        Instructor currentInstructor = (Instructor) instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        currentInstructor.setFirstName(instructor.getFirstName());
        currentInstructor.setLastName(instructor.getLastName());
        instructorRepository.save(currentInstructor);
    }

    @PatchMapping(value = "/setAvailabilities", consumes = "application/json")
    public Instructor setAvailabilities(@RequestBody List<City> availabilities) throws InvalidCityNameException {
        Instructor instructor = (Instructor) instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setAvailabilities(getCityList(availabilities));
        return instructorRepository.save(instructor);
    }

    @PatchMapping(value = "/setSpecializations", consumes = "application/json")
    public Instructor setSpecializations(@RequestBody List<Activity> specializations) throws InvalidActivityNameException {
        Instructor instructor = (Instructor) instructorRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        instructor.setSpecializations(getActivityList(specializations));
        return instructorRepository.save(instructor);
    }

    private List<City> getCityList(List<City> uncheckedCityList) throws InvalidCityNameException {
        List<City> cityList = uncheckedCityList.stream().map(city -> cityRepository.findByName(city.getName())).toList();
        // If any city is not found, throw an exception
        List<String> invalidCityNames = uncheckedCityList.stream().map(City::getName).filter(name -> cityList.stream().noneMatch(city -> city.getName().equals(name))).toList();
        if (!invalidCityNames.isEmpty()) {
            throw new InvalidCityNameException(invalidCityNames);
        }
        return cityList;
    }

    private List<Activity> getActivityList(List<Activity> uncheckedActivityList) throws InvalidActivityNameException {
        List<Activity> activityList = uncheckedActivityList.stream().map(activity -> activityRepository.findByName(activity.getName())).toList();
        // If any activity is not found, throw an exception
        List<String> invalidActivityNames = uncheckedActivityList.stream().map(Activity::getName).filter(name -> activityList.stream().noneMatch(activity -> activity.getName().equals(name))).toList();
        if (!invalidActivityNames.isEmpty()) {
            throw new InvalidActivityNameException(invalidActivityNames);
        }
        return activityList;
    }
}
