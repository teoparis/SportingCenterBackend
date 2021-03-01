package com.sportingCenterWebApp.calendarservice.service;

import com.sportingCenterWebApp.calendarservice.dto.Activity;
import com.sportingCenterWebApp.calendarservice.dto.Subscription;
import com.sportingCenterWebApp.calendarservice.dto.User;
import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import com.sportingCenterWebApp.calendarservice.utils.GeneralUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;


    public List<Event> getTodayEvents() {
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> todayEvents = new ArrayList<>();
        DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        now = GeneralUtils.removeTime(now);
        for (Event event : allEvents) {
            String stringDataEventoNonReversed = StringUtils.substringBefore(event.getInizio(), "T");
            String stringDataEvento = GeneralUtils.aggiustaStringData(stringDataEventoNonReversed);
            Date dataEvento = null;
            try {
                dataEvento = dateFormat.parse(stringDataEvento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (now.equals(dataEvento)) {
                todayEvents.add(event);
            }
        }
        return todayEvents;
    }

    public List<Event> getEventsForUser(Long subId) {
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
            Date dataEvento = null;
            try {
                dataEvento = dateFormat.parse(stringDataEvento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(getActivityById(subActivities, event.getActivityId()) != null && (now.before(dataEvento) || now.equals(dataEvento))){
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

    public List<Event> findAllById(List<Long> userEventsIds) {
        List<Event> userEvents = (List<Event>) eventRepository.findAllById(userEventsIds);
        return userEvents;
    }

    public List<Event> findAll() {
        return (List<Event>)eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public void delete(Event event) {
        //Delete all bookings with this event


        //Delete the event
        eventRepository.delete(event);
    }

}
