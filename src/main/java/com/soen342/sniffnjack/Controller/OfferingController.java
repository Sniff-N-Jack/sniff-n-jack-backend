package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.*;
import com.soen342.sniffnjack.Exceptions.*;
import com.soen342.sniffnjack.Repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/offerings")
@Transactional
public class OfferingController {
    @Autowired
    private OfferingRepository offeringRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/all")
    public List<Offering> getAllOfferings() {
        List<Offering> offerings = offeringRepository.findAll();
        for (Offering offering : offerings) {
            // Check and log the lesson for debugging
            if (offering.getLesson() == null) {
                System.out.println("Offering " + offering.getId() + " has no lesson.");
            }
        }
        return offerings;
    }
    

    @GetMapping("/get")
    public Offering getOfferingById(@RequestParam Long id) {
        return offeringRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public Offering addOffering(@RequestParam Long lessonId, @RequestParam Long instructorId)
            throws InvalidLessonException, UserNotFoundException, InvalidCityException {


        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if (lesson == null) {
            throw new InvalidLessonException(lessonId); // Handle if the lesson is not found
        }


        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            throw new UserNotFoundException(instructorId); // Handle if the instructor is not found
        }

        assert instructor.getAvailabilities() != null;
        if (!instructor.getAvailabilities().contains(lesson.getLocation().getCity())) {
            throw new InvalidCityException(lesson.getLocation().getCity().getName());
        }

        Offering offering = new Offering(instructor, lesson);
        return offeringRepository.save(offering);
    }

    @PatchMapping("/update")
    public Offering updateOffering(@RequestBody Offering offering) {
        return offeringRepository.save(offering);
    }

    @PatchMapping("/take")
    public Offering takeOffering(@RequestParam Long offeringId, @RequestParam Long instructorId)
            throws InvalidOfferingException, UserNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            throw new UserNotFoundException(instructorId);
        }
        Offering offering = offeringRepository.findById(offeringId).orElse(null);
        if (offering == null) {
            throw new InvalidOfferingException();
        }
        offering.setInstructor(instructor);
        return offeringRepository.save(offering);
    }

    @DeleteMapping("/delete")
    public void deleteOffering(@RequestParam Long id) throws InvalidOfferingException, CustomBadRequestException{
        if (!offeringRepository.existsById(id)) {
            throw new InvalidOfferingException();
        }
        try {
            offeringRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomBadRequestException("Offering is referenced by other entities");
        }
    }
    
}
