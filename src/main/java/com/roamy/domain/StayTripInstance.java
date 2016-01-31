package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("STAY")
public class StayTripInstance extends TripInstance {

    @Override
    public String getType() {
        return "STAY";
    }

    @Override
    public String toString() {
        return "StayTripInstance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", displayStartDate=" + displayStartDate +
                ", displayEndDate=" + displayEndDate +
                '}';
    }
}
