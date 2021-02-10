package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.EventRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainer")
public class EventTrainerController {

    private final EventRepository eventRepository;

    public EventTrainerController(EventRepository abbRepository) {
        this.eventRepository = abbRepository;
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
