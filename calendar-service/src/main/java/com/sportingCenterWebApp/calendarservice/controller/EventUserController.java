package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.service.BookingService;
import com.sportingCenterWebApp.calendarservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class EventUserController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;


    @RequestMapping(value = "events/{subId}", method = RequestMethod.GET)
    public List<Event> getEventsForUser(@PathVariable("subId") Long subId) throws ParseException {
        return eventService.getEventsForUser(subId);
    }

    @RequestMapping(value = "delete_booking/{userId}/{eventId}", method = RequestMethod.PUT)
    public void deleteBooking(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId){
        bookingService.deleteBooking(userId,eventId);
    }


    @PutMapping("events/bookings/{idUser}/{eventId}")
    public void bookEvent(@PathVariable("idUser") Long userId, @PathVariable("eventId") Long eventId) {
        bookingService.bookEvent(userId,eventId);
    }

    @RequestMapping(value = "events/bookings/{userId}", method = RequestMethod.GET)
    public List<Event> getBookingForUser(@PathVariable("userId") Long userId) {
        return eventService.findAllById(bookingService.getBookingsIdForUserId(userId));
    }
}
