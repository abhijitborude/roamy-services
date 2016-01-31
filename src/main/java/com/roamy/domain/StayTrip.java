package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("STAY")
public class StayTrip extends Trip {

    @Override
    public String getType() {
        return "STAY";
    }

    @Override
    public String toString() {
        return "StayTrip{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
