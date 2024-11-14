package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.Lesson;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidLocationException;
import com.soen342.sniffnjack.Exceptions.OverlappingLessonsException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import com.soen342.sniffnjack.Repository.LessonRepository;
import com.soen342.sniffnjack.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private LocationRepository locationRepository;

    private void checkLesson(Lesson lesson) throws InvalidActivityNameException, InvalidLocationException {
        if (lesson.getActivity().getId() == null) {
            Activity activity = activityRepository.findByName(lesson.getActivity().getName());
            if (activity == null) {
                throw new InvalidActivityNameException(lesson.getActivity().getName());
            }
            lesson.setActivity(activity);
        }

        if (lesson.getLocation().getId() == null) {
            throw new InvalidLocationException();
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
    public Lesson addLesson(@RequestBody Lesson lesson) throws InvalidActivityNameException, InvalidLocationException, OverlappingLessonsException {
        if (lesson.getActivity() == null || lesson.getActivity().getId() == null) {
            throw new InvalidActivityNameException("Activity is required and must have a valid ID");
        }
        if (lesson.getLocation() == null || lesson.getLocation().getId() == null) {
            throw new InvalidLocationException();
        }

        if (lessonRepository.findAll().stream().anyMatch(l -> l.isOverlapping(lesson))) {
            throw new OverlappingLessonsException();
        }

        lesson.setActivity(activityRepository.findById(lesson.getActivity().getId())
            .orElseThrow(() -> new InvalidActivityNameException("Invalid activity ID")));
    
        lesson.setLocation(locationRepository.findById(lesson.getLocation().getId())
            .orElseThrow(InvalidLocationException::new));
    
        return lessonRepository.save(lesson);
    }
    

    @DeleteMapping("/delete")
    public void deleteLesson(@RequestParam Long id) {
        lessonRepository.deleteById(id);
    }

    @PatchMapping("/update")
    public Lesson updateLesson(@RequestBody Lesson lesson) throws InvalidActivityNameException, InvalidLocationException {
        return lessonRepository.save(lesson);
    }
}
