package com.sportingCenterWebApp.calendarservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Booking")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private long id;

    @Column(name = "event_id")
    private long event_id;

    @Column(name = "user_id")
    private long user_id;

    public Booking(){

    }

    public Booking(long event_id, long user_id) {
        this.event_id = event_id;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", user_id=" + user_id +
                '}';
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
