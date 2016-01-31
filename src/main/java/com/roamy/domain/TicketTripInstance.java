package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("TICKET")
public class TicketTripInstance extends TripInstance {

    @Override
    public String getType() {
        return "TICKET";
    }

    @Override
    public String toString() {
        return "TicketTripInstance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", displayStartDate=" + displayStartDate +
                ", displayEndDate=" + displayEndDate +
                '}';
    }
}
