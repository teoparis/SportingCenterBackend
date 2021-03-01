package com.sportingCenterWebApp.calendarservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "dataFine")
    private String dataFine;

    @Column(name = "dataInizio")
    private final String inizio;

    @Column(name = "activityId")
    private String activityId;

    @Column(name = "numberPartecipants")
    private Long number;

    public Event() {
        this.title ="";
        this.activityId ="";
        this.dataFine ="";
        this.inizio="";
    }

    public String getTitle() {
        return this.title;
    }

    public String getActivityId(){
        return this.activityId;
    }

    public String getInizio() {
        return this.inizio;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "{ "+ "Id: " +  this.id + " Name: " + this.title + " " + this.dataFine + " " + this
        .inizio + " actid: " + this.activityId;
    }
}


