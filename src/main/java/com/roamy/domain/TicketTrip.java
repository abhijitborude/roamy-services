package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("TICKET")
public class TicketTrip extends Trip {

    @Override
    public String getType() {
        return "TICKET";
    }

    @Override
    public String toString() {
        return "TicketTrip{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
