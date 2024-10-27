package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Exceptions.ActivityAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Activity> addMultipleActivities(@RequestBody List<Activity> activities) throws Exception {
        getActivityList(activities, true);
        return activityRepository.saveAll(activities);
    }

    @DeleteMapping("/delete")
    public void deleteActivity(@RequestParam String name) throws InvalidActivityNameException {
        if (!activityRepository.existsByName(name)) {
            throw new InvalidActivityNameException(name);
        }
        activityRepository.deleteByName(name);
    }

    @DeleteMapping("/deleteMultiple")
    public void deleteMultipleActivities(@RequestBody List<Activity> activities) throws Exception {
        activities = getActivityList(activities, false);
        activityRepository.deleteAll(activities);
    }

    private List<Activity> getActivityList(List<Activity> uncheckedActivityList, boolean adding) throws Exception {
        List<Activity> activityList = uncheckedActivityList.stream().map(activity -> activityRepository.findByName(activity.getName())).toList();
        if (adding && !activityList.isEmpty())
            throw new ActivityAlreadyExistsException(activityList.stream().map(Activity::getName).toList());
        else if (!adding) {
            List<String> invalidActivityNames = uncheckedActivityList.stream().map(Activity::getName).filter(name -> activityList.stream().noneMatch(activity -> activity.getName().equals(name))).toList();
            if (!invalidActivityNames.isEmpty()) throw new InvalidActivityNameException(invalidActivityNames);
        }

        return activityList;
    }
}
