package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Lesson;
import com.soen342.sniffnjack.Exceptions.CustomBadRequestException;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidLocationException;
import com.soen342.sniffnjack.Exceptions.OverlappingLessonsException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.LessonRepository;
import com.soen342.sniffnjack.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private LocationRepository locationRepository;

    private void checkLesson(Lesson lesson)
            throws InvalidActivityNameException, InvalidLocationException, OverlappingLessonsException, CustomBadRequestException{
        if (lesson.getActivity() == null || lesson.getActivity().getId() == null) {
            throw new InvalidActivityNameException("Activity is required and must have a valid ID");
        }
        if (lesson.getLocation() == null || lesson.getLocation().getId() == null) {
            throw new InvalidLocationException();
        }

        if (lesson.getStartDate().isBefore(LocalDate.now())) {
            throw new CustomBadRequestException("Start date must be at least today");
        } else if (lesson.getStartDate().isEqual(LocalDate.now()) && lesson.getDayOfWeek() == LocalDate.now().getDayOfWeek() && lesson.getStartTime().isBefore(LocalTime.now(Clock.system(ZoneOffset.of("-05:00"))))) {
            throw new CustomBadRequestException("Start time must be at least now");

        }

        if (lessonRepository.findAll().stream().anyMatch(l -> l.isOverlapping(lesson, false) && !l.getId().equals(lesson.getId()))) {
            throw new OverlappingLessonsException();
        }

        if (lesson.getTotalSpots() < 1) {
            throw new CustomBadRequestException("Total spots must be greater than 0");
        }
    }

    @GetMapping("/all")
    public Iterable<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @GetMapping("/get")
    public Lesson getLessonById(@RequestParam Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public Lesson addLesson(@RequestBody Lesson lesson)
            throws InvalidActivityNameException, InvalidLocationException, OverlappingLessonsException, CustomBadRequestException {
        checkLesson(lesson);

        lesson.setActivity(activityRepository.findById(lesson.getActivity().getId())
            .orElseThrow(() -> new InvalidActivityNameException("Invalid activity ID")));
    
        lesson.setLocation(locationRepository.findById(lesson.getLocation().getId())
            .orElseThrow(InvalidLocationException::new));
    
        return lessonRepository.save(lesson);
    }
    

    @DeleteMapping("/delete")
    public void deleteLesson(@RequestParam Long id) throws CustomBadRequestException {

        try {
            lessonRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomBadRequestException("Lesson is referenced by other entities");
        }
    }

    @PatchMapping("/update")
    public Lesson updateLesson(@RequestBody Lesson lesson)
            throws InvalidActivityNameException, InvalidLocationException, OverlappingLessonsException, CustomBadRequestException {
        checkLesson(lesson);
        return lessonRepository.save(lesson);
    }
}
