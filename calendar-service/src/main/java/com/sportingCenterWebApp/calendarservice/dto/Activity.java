package com.sportingCenterWebApp.calendarservice.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Activity {

    private long id;

    private final String name;

    private final String descr;

    private Boolean fitness;

    private Boolean nuoto;


    public Activity() {
        this.name = "";
        this.descr = "";
    }

    public Activity(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public Boolean getFitness() {
        return fitness;
    }
    public Boolean getNuoto() {
        return nuoto;
    }

    @Override
    public String toString() {
        return "{ "+ "Id: " +  this.id + " Name: " + this.name + " Descr: " + this.descr + " }";
    }
}

