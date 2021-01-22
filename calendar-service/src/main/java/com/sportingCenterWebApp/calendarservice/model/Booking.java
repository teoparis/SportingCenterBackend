package com.sportingCenterWebApp.calendarservice.model;

import javax.persistence.*;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOKING_ID")
    private long booking_id;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "EVENT_ID")
    private long event_id;
}
