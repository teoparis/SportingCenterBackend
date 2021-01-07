package com.sportingCenterWebApp.activityservice.controller;

import com.sportingCenterWebApp.activityservice.model.Activity;
import com.sportingCenterWebApp.activityservice.repo.ActivityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
