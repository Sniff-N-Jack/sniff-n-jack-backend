package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Exceptions.ActivitiesAlreadyExistException;
import com.soen342.sniffnjack.Exceptions.ActivityAlreadyExistsException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/all")
    public Iterable<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @PostMapping("/add")
    public Activity addActivity(@RequestParam String name) throws ActivityAlreadyExistsException {
        if (activityRepository.existsByName(name)) {
            throw new ActivityAlreadyExistsException(name);
        }
        return activityRepository.save(new Activity(name));
    }

    @PostMapping("/addMultiple")
    public Iterable<Activity> addMultipleActivities(@RequestParam String[] names) throws ActivitiesAlreadyExistException {
        List<Activity> activities = activityRepository.findDistinctByNameIn(Arrays.stream(names).toList());
        if (!activities.isEmpty()) {
            throw new ActivitiesAlreadyExistException(activities.stream().map(Activity::getName).toArray(String[]::new));
        }
        for (String name : names) {
            activities.add(activityRepository.save(new Activity(name)));
        }
        return activities;
    }

    @DeleteMapping("/delete")
    public void deleteActivity(@RequestParam String name) {
        activityRepository.deleteByName(name);
    }

    @DeleteMapping("/deleteMultiple")
    public void deleteMultipleActivities(@RequestParam String[] names) {
        for (String name : names) {
            activityRepository.deleteByName(name);
        }
    }
}
