package com.sportingCenterWebApp.activityservice.controller;

import com.sportingCenterWebApp.activityservice.model.Activity;
import com.sportingCenterWebApp.activityservice.repo.ActivityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/all")
public class AllActivityController {
    private final ActivityRepository activityRepository;

    public AllActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    @RequestMapping(value = "activities", method = RequestMethod.GET)
    public @ResponseBody List<Activity> getActivities() {
        return (List<Activity>) activityRepository.findAll();
    }
}
