package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.model.User;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class EventController {

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public EventController(EventRepository abbRepository, BookingRepository bookingRepository) {
        this.eventRepository = abbRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @PostMapping("/event")
    void addEvent(@RequestBody Event event){
        eventRepository.save(event);
    }

    @PostMapping("/event/delete")
    void deleteEvent(@RequestBody Event event) {
        eventRepository.delete(event);
    }

    @GetMapping("/getusers/{eventId}")
    public List<User> getUsersForEvent(@PathVariable("eventId") Long eventId) {
        System.out.println(eventId);
        List<Booking> bookingList = bookingRepository.findBookingsByEventid(eventId);
        List<Long> usersId = new ArrayList<>();
        for (Booking booking : bookingList) {
            usersId.add(booking.getUser_id());
        }

        //Get All Users
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://authentication-service/api/auth/users", User[].class);
        User[] users = responseEntity.getBody();

        //Get users with ids in usersids
        List<User> usersForEvent = new ArrayList<>();
        for (User user : users){
            if (usersId.contains(user.getId())) {
                usersForEvent.add(user);
            }
        }
        return usersForEvent;
    }
}

