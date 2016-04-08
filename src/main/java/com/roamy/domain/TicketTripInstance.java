package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("TICKET")
public class TicketTripInstance extends TripInstance {

    @Column(name = "ITINERARY", length = DbConstants.LONG_TEXT)
    private String activity;

    @Column(name = "MEETING_POINTS", length = DbConstants.LONG_TEXT)
    private String location;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
