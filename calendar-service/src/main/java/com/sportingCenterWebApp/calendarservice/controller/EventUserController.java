package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Activity;
import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.utils.GeneralUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.ObjLongConsumer;

@RestController
@RequestMapping("/user")
public class EventUserController {
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public EventUserController(EventRepository abbRepository, BookingRepository bookingRepository) {

        this.eventRepository = abbRepository;
        this.bookingRepository = bookingRepository;
    }

    @RequestMapping(value = "events/{subId}", method = RequestMethod.GET)
    public List<Event> getEventsForUser(@PathVariable("subId") Long subId) throws ParseException {

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

        //Get only events compatible with user subscritpion and not in the past
        DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        now = GeneralUtils.removeTime(now);

        List<Event> allEvents = (List<Event>)eventRepository.findAll();
        List<Event> subEvents = new ArrayList<>();
        for(Event event : allEvents) {
            String stringDataEventoNonReversed = StringUtils.substringBefore(event.getInizio(), "T");
            String stringDataEvento = GeneralUtils.aggiustaStringData(stringDataEventoNonReversed);
            Date dataEvento = dateFormat.parse(stringDataEvento);
            if(getActivityById(subActivities, event.getActivityId()) != null && now.before(dataEvento)){
                subEvents.add(event);
            }
        }

        return subEvents;
    }

    @RequestMapping(value = "delete_booking/{userId}/{eventId}", method = RequestMethod.PUT)
    public void deleteBooking(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId){
        bookingRepository.deleteByUserEventId(userId, eventId);
    }


    @PutMapping("events/bookings/{idUser}/{eventId}")
    public void bookEvent(@PathVariable("idUser") Long userId, @PathVariable("eventId") Long eventId) {
        bookingRepository.save(new Booking(eventId,userId));
    }

    @RequestMapping(value = "events/bookings/{userId}", method = RequestMethod.GET)
    public List<Event> getBookingForUser(@PathVariable("userId") Long userId) {
        List<Booking> userBookings = (List<Booking>) bookingRepository.findBookingsByUserid(userId);
        List<Long> userEventsIds = new ArrayList<>();
        for (Booking booking : userBookings) {
            userEventsIds.add(booking.getEvent_id());
        }

        List<Event> userEvents = (List<Event>) eventRepository.findAllById(userEventsIds);

        return userEvents;
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
