package com.sportingCenterWebApp.calendarservice.repo;

import com.sportingCenterWebApp.calendarservice.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("select b from Booking b where b.user_id = :id")
    List<Booking> findBookingsByUserid(Long id);

    @Query("select b from Booking b where b.event_id = :id")
    List<Booking> findBookingsByEventid(Long id);
}
