package com.sportingCenterWebApp.subscriptionservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Subscription")
@Getter
@Setter

public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACT_ID")
    private long id;
    @Column(name = "NAME")
    private final String name;
    @Column(name = "DESCR")
    private final String descr;

    @Column(name = "durataMesi")
    private int durataMesi;
    @Column(name = "ingressiSettimanali")
    private int ingressiSettimanali;
    @Column(name = "numeroIngressiTotale")
    private int numeroIngressiTotale;
    @Column(name = "fitness")
    private Boolean fitness;
    @Column(name = "nuoto")
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

    @Override
    public String toString() {
        return "{ "+ "Id: " +  this.id + " Name: " + this.name + " Descr: " + this.descr + " }";
    }
}
