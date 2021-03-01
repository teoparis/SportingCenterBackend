package com.sportingCenterWebApp.calendarservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 65981149772133526L;

    private Long id;


    private String providerUserId;

    private String email;


    private boolean enabled;


    private String displayName;


    private String dataNascita;


    protected Date createdDate;


    protected Date modifiedDate;

    private String password;

    private String provider;


    private String number;


    private String abbonamento;


    private String dataScadenza;


    private Boolean expired = false;

    public Long getId() {
        return this.id;
    }

}
