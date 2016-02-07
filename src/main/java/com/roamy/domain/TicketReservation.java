package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("TICKET")
public class TicketReservation extends Reservation {

    @Override
    public String getType() {
        return "TICKET";
    }

    @Override
    public String toString() {
        return "TicketReservation{" +
                "tripInstances=" + tripInstances +
                ", user=" + user +
                ", numberOfRoamies=" + numberOfRoamies +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
