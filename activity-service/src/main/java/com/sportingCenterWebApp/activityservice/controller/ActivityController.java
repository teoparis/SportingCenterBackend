package com.sportingCenterWebApp.activityservice.controller;

import com.sportingCenterWebApp.activityservice.model.Activity;
import com.sportingCenterWebApp.activityservice.repo.ActivityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/activities")
    public List<Activity> getActivities() {
        return (List<Activity>) activityRepository.findAll();
    }

    @PostMapping("/activities/delete")
    void deleteActivity(@RequestBody Activity activity) {
        activityRepository.delete(activity);
    }

    @PostMapping("/activity")
    void addActivity(@RequestBody Activity activity){
        activityRepository.save(activity);
    }

}
