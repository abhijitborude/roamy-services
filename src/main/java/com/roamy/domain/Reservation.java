package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "RESERVATION", schema = "ROAMY")
public class Reservation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRIP_INSTANCE_ID")
    @JsonIgnore
    private TripInstance tripInstance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "NUMBER_OF_TRAVELLERS")
    private int numberOfTravellers;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String email;

    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                "numberOfTravellers=" + numberOfTravellers +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
