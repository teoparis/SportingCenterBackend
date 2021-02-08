package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class EventController {
    //standard constructors
    private final EventRepository eventRepository;

    public EventController(EventRepository abbRepository) {
        this.eventRepository = abbRepository;
    }

    @GetMapping("/events")
    public List<Event> getActivities() {
        return (List<Event>) eventRepository.findAll();
    }

    @PostMapping("/event")
    void addActivity(@RequestBody Event event){
        System.out.println(event);
        eventRepository.save(event);
    }

    @PostMapping("/event/delete")
    void deleteActivity(@RequestBody Event event) {
        eventRepository.delete(event);
    }
}

