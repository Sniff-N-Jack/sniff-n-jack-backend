package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Exceptions.ActivityAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.CustomBadRequestException;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/activities")
@Transactional
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

    @PostMapping(value = "/addMultiple")
    public Iterable<Activity> addMultipleActivities(@RequestParam List<String> names) throws Exception {
        getActivityList(names, true);
        return activityRepository.saveAll(names.stream().map(Activity::new).toList());
    }

    @DeleteMapping("/delete")
    public void deleteActivity(@RequestParam String name) throws InvalidActivityNameException, CustomBadRequestException {
        if (!activityRepository.existsByName(name)) {
            throw new InvalidActivityNameException(name);
        }
        try {
            activityRepository.deleteByName(name);
        } catch (Exception e) {
            throw new CustomBadRequestException("Activity is referenced by other entities");
        }
    }

    @DeleteMapping(value = "/deleteMultiple")
    public void deleteMultipleActivities(@RequestParam List<String> names) throws Exception {
        List<Activity> activities = getActivityList(names, false);
        try {
            activityRepository.deleteAll(activities);
        } catch (Exception e) {
            throw new CustomBadRequestException("Some activities could not be deleted because they are referenced by other entities");
        }
    }

    @PatchMapping("/update")
    public Activity updateActivity(@RequestParam String oldName, @RequestParam String newName) throws Exception {
        if (activityRepository.existsByName(newName)) {
            throw new ActivityAlreadyExistsException(newName);
        }
        Activity activity = activityRepository.findByName(oldName);
        if (activity == null) {
            throw new InvalidActivityNameException(oldName);
        }
        activity.setName(newName);
        return activityRepository.save(activity);
    }

    private List<Activity> getActivityList(List<String> uncheckedActivityList, boolean adding) throws Exception {
        List<Activity> activityList = uncheckedActivityList.stream().map(activity -> activityRepository.findByName(activity)).filter(Objects::nonNull).toList();
        if (adding && !activityList.isEmpty())
            throw new ActivityAlreadyExistsException(activityList.stream().map(Activity::getName).toList());
        else if (!adding) {
            List<String> invalidActivityNames = uncheckedActivityList.stream().filter(name -> activityList.stream().noneMatch(activity -> activity.getName().equals(name))).toList();
            if (!invalidActivityNames.isEmpty()) throw new InvalidActivityNameException(invalidActivityNames);
        }

        return activityList;
    }
}
