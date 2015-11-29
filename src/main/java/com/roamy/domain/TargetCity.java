package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;

import javax.persistence.*;

/**
 * Created by Abhijit on 24/10/15.
 */
@Entity
@Table(name = "TARGET_CITY", schema = "ROAMY")
public class TargetCity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    @JsonIgnore
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "INSTRUCTIONS", length = DbConstants.LONG_TEXT)
    private String instructions;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
