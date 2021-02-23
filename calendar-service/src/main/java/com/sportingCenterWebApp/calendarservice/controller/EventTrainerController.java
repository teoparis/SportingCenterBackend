package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Activity;
import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.model.User;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
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

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public EventTrainerController(EventRepository eventRepository, BookingRepository bookingRepository) {
        this.eventRepository = eventRepository;
        this.bookingRepository = bookingRepository;
    }


    @GetMapping("/todayevents")
    public List<Event> getTodayEvents() throws ParseException {
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> todayEvents = new ArrayList<>();
        DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        now = removeTime(now);
        for (Event event : allEvents) {
            String stringDataEventoNonReversed = StringUtils.substringBefore(event.getInizio(), "T");
            String stringDataEvento = aggiustaStringData(stringDataEventoNonReversed);
            Date dataEvento = dateFormat.parse(stringDataEvento);
            if (now.equals(dataEvento)) {
                todayEvents.add(event);
            }
        }
        return todayEvents;
    }

    @PostMapping("/setuserspresence/{eventId}")
    public void setUsersPresence(@PathVariable("eventId") Long eventId, @Valid @RequestBody List<User> userList){
        List<Long> usersIdList = new ArrayList<>();
        for (User user: userList) {
            System.out.println("Id: " + user.getId());
            usersIdList.add(user.getId());
        }

        List<Booking> bookingsList = (List<Booking>) bookingRepository.findAll();
        for (Booking booking: bookingsList) {
            if(booking.getEvent_id() == eventId) {
                if (usersIdList.contains(booking.getUser_id())){
                    booking.setPresence();
                    bookingRepository.save(booking);
                }
            }
        }
    }

    @GetMapping("/getusers/{eventId}")
    public List<User> getUsersForEvent(@PathVariable("eventId") Long eventId) {
        System.out.println(eventId);
        List<Booking> bookingList = bookingRepository.findBookingsByEventid(eventId);
        List<Long> usersId = new ArrayList<>();
        for (Booking booking : bookingList) {
            if(!booking.getPresence()) {
                usersId.add(booking.getUser_id());
            }
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


    private String aggiustaStringData(String stringaData) {
        String[] arrayString = stringaData.split("-");
        String stringaDataAggiustata = arrayString[2] + "-" + arrayString[1] + "-" + arrayString[0];
        return stringaDataAggiustata;
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
