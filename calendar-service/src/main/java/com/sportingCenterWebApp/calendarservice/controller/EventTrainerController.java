package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.dto.User;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.service.BookingService;
import com.sportingCenterWebApp.calendarservice.service.EventService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trainer")
public class EventTrainerController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;


    @GetMapping("/todayevents")
    public List<Event> getTodayEvents() {
        return eventService.getTodayEvents();
    }

    @PostMapping("/setuserspresence/{eventId}")
    public void setUsersPresence(@PathVariable("eventId") Long eventId, @Valid @RequestBody List<User> userList){
        bookingService.setUsersPresence(eventId,userList);
    }

    @GetMapping("/getusers/{eventId}")
    public List<User> getUsersForEvent(@PathVariable("eventId") Long eventId) {
        return bookingService.getUsersForEvent(eventId);
    }




}
