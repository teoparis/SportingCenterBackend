package com.sportingCenterWebApp.calendarservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")


public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long id;

    @Column(name = "start")
    private Date dataInizio;

    @Column(name = "end")
    private Date dataFine;

    @Column(name = "title")
    private String title;

    @Column(name = "allDay")
    private Boolean allDay;

}
