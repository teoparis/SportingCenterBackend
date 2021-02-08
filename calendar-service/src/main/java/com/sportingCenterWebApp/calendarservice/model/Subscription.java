package com.sportingCenterWebApp.calendarservice.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter

public class Subscription {

    private long id;

    private final String name;

    private final String descr;


    private int durataMesi;

    private int ingressiSettimanali;

    private int numeroIngressiTotale;

    private Boolean fitness;

    private Boolean nuoto;

    public Subscription() {
        this.name = "";
        this.descr = "";
        this.durataMesi=0;
        this.ingressiSettimanali=0;
        this.numeroIngressiTotale=0;
        this.nuoto=false;
        this.fitness=false;
    }

    public Subscription(String name, String descr) {
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

