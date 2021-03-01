package com.sportingCenterWebApp.calendarservice.service;

import com.sportingCenterWebApp.calendarservice.dto.User;
import com.sportingCenterWebApp.calendarservice.model.Booking;
import com.sportingCenterWebApp.calendarservice.model.Event;
import com.sportingCenterWebApp.calendarservice.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getUsersForEvent(Long eventId) {
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

    public void setUsersPresence(Long eventId, List<User> userList) {
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

    public List<Long> getBookingsIdForUserId(Long userId) {
        List<Booking> userBookings = (List<Booking>) bookingRepository.findBookingsByUserid(userId);
        List<Long> userEventsIds = new ArrayList<>();
        for (Booking booking : userBookings) {
            userEventsIds.add(booking.getEvent_id());
        }
        return userEventsIds;
    }

    public void deleteBooking(Long userId, Long eventId){
        bookingRepository.deleteByUserEventId(userId, eventId);
    }

    public void bookEvent(Long userId, Long eventId) {
        bookingRepository.save(new Booking(eventId,userId));
    }

    public void deleteBookingsForEventId(Long eventId) {
        bookingRepository.deleteByEventId(eventId);
    }

    public List<User> getPresences(Long eventId) {
        //Get All Users
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://authentication-service/api/auth/users", User[].class);
        User[] users = responseEntity.getBody();
        List<User> userList = Arrays.asList(users);

        List<User> usersPresent = new ArrayList<>();
        for (Booking booking : bookingRepository.findBookingsByEventid(eventId)) {
            if (booking.getPresence()) {
                for (User user : userList) {
                    if (user.getId() == booking.getUser_id()) {
                        usersPresent.add(user);
                    }
                }
            }
        }
        return usersPresent;
    }
}
