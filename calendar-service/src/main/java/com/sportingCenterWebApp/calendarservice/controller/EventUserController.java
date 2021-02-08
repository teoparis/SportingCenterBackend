package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Activity;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class EventUserController {
    private final EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;

    public EventUserController(EventRepository abbRepository) {
        this.eventRepository = abbRepository;
    }

    @RequestMapping(value = "events/{subId}", method = RequestMethod.GET)
    public List<Event> getEventsForUser(@PathVariable("subId") Long subId) {

        //Get Subscription From Subscription Microservice
        Subscription userSubscription = restTemplate.getForObject("http://subscription-service/all/subscriptions/getSubfromid/{subId}",
                Subscription.class, subId);
        Boolean nuoto = userSubscription.getNuoto();
        Boolean fitness = userSubscription.getFitness();

        //Get All Activities
        ResponseEntity<Activity[]> responseEntity = restTemplate.getForEntity("http://activity-service/all/activities", Activity[].class);
        Activity[] activities = responseEntity.getBody();

        //Get Only Activities With Subscription Type
        List<Activity> subActivities = new ArrayList<>();
        for(Activity activity : activities) {
            if (activity.getFitness() == fitness && activity.getNuoto() == nuoto) {
                subActivities.add(activity);
            }
        }

        //Get only events compatible with user subscritpion
        List<Event> allEvents = (List<Event>)eventRepository.findAll();
        List<Event> subEvents = new ArrayList<>();
        for(Event event : allEvents) {
            if(getActivityById(subActivities, event.getActivityId()) != null){
                subEvents.add(event);
            }
        }

        return subEvents;
    }

    private Activity getActivityById(List<Activity> subActivities, String id) {
        for (Activity activity : subActivities) {
            if (Long.toString(activity.getId()).equals(id)){
                return activity;
            }
        }
        return null;
    }

}
