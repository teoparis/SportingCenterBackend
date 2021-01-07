package com.sportingCenterBackEnd.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Activity")
@Getter
@Setter

public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ACT_ID")
  private long id;
  @Column(name = "NAME")
  private final String name;
  @Column(name = "DESCR")
  private final String descr;


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

  @Override
  public String toString() {
    return "{ "+ "Id: " +  this.id + " Name: " + this.name + " Descr: " + this.descr + " }";
  }
}
