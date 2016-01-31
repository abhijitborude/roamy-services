package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("PACKAGE")
public class PackageTripInstance extends TripInstance {

    @Override
    public String getType() {
        return "PACKAGE";
    }

    @Column(name = "NUMBER_OF_DAYS")
    private int numberOfDays;

    @Column(name = "ITINERARY", length = DbConstants.LONG_TEXT)
    private String itinerary;

    @Column(name = "INCLUSIONS", length = DbConstants.LONG_TEXT)
    private String inclusions;

    @Column(name = "EXCLUSIONS", length = DbConstants.LONG_TEXT)
    private String exclusions;

    @Column(name = "MEETING_POINTS", length = DbConstants.LONG_TEXT)
    private String meetingPoints;

    @Column(name = "THINGS_TO_CARRY", length = DbConstants.LONG_TEXT)
    private String thingsToCarry;

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getMeetingPoints() {
        return meetingPoints;
    }

    public void setMeetingPoints(String meetingPoints) {
        this.meetingPoints = meetingPoints;
    }

    public String getThingsToCarry() {
        return thingsToCarry;
    }

    public void setThingsToCarry(String thingsToCarry) {
        this.thingsToCarry = thingsToCarry;
    }

    @Override
    public String toString() {
        return "PackageTripInstance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", displayStartDate=" + displayStartDate +
                ", displayEndDate=" + displayEndDate +
                '}';
    }
}
