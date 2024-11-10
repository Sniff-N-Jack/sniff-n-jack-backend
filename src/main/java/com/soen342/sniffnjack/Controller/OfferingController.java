package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.*;
import com.soen342.sniffnjack.Exceptions.*;
import com.soen342.sniffnjack.Repository.*;
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
    public Iterable<Offering> getAllOfferings() {
        return offeringRepository.findAll();
    }

    @GetMapping("/get")
    public Offering getOfferingById(@RequestParam Long id) {
        return offeringRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public Offering addOffering(@RequestBody Long lessonId, @RequestParam Long instructorId) throws InvalidLessonException, UserNotFoundException, OverlappingLessonsException {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if (lesson == null) {
            throw new InvalidLessonException(lessonId);
        }

        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            throw new UserNotFoundException(instructorId);
        }

        List<Offering> offerings = offeringRepository.findAllByInstructorId(instructorId);
        for (Offering offering : offerings) {
            if (offering.getLesson().getDayOfWeek() == lesson.getDayOfWeek()) {
                LocalTime lessonStartTime = lesson.getStartTime();
                LocalTime lessonEndTime = lesson.getEndTime();
                LocalTime offeringStartTime = offering.getLesson().getStartTime();
                LocalTime offeringEndTime = offering.getLesson().getEndTime();
                if ((lessonStartTime.isAfter(offeringStartTime) && lessonStartTime.isBefore(offeringEndTime)) ||
                        (lessonEndTime.isAfter(offeringStartTime) && lessonEndTime.isBefore(offeringEndTime)) ||
                        (lessonStartTime.isBefore(offeringStartTime) && lessonEndTime.isAfter(offeringEndTime)) ||
                        lessonStartTime.equals(offeringStartTime) ||
                        lessonEndTime.equals(offeringEndTime)
                    ) {
                    throw new OverlappingLessonsException();
                }

            }
        }

        return offeringRepository.save(new Offering(instructor, lesson));
    }

    @PatchMapping("/update")
    public Offering updateOffering(@RequestBody Offering offering) {
        return offeringRepository.save(offering);
    }

    @PatchMapping("/take")
    public Offering takeOffering(@RequestParam Long offeringId, @RequestParam Long instructorId) throws InvalidOfferingException, UserNotFoundException {
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
    public void deleteOffering(@RequestParam Long id) {
        offeringRepository.deleteById(id);
    }
}
