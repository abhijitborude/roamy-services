package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "TRIP_INSTANCE", schema = "ROAMY")
public class TripInstance extends AbstractEntity {

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    protected String name;

    @Column(name = "DESCRIPTION", length = DbConstants.MEDIUM_TEXT)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    @JsonIgnore
    private Trip trip;

    @Column(name = "NUMBER_OF_DAYS")
    private int numberOfDays;

    @Column(name = "ADDITIONAL_DESCRIPTION", length = DbConstants.LONG_TEXT)
    private String additionalDescription;

    @Column(name = "DIFFICULTY_LEVEL")
    private int difficultyLevel;

    @Column(name = "PRICE_PER_ADULT")
    private Double pricePerAdult;

    @Column(name = "TAC")
    private Double tac;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_INSTANCE_CITY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_INSTANCE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CITY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    private List<City> targetCities;

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

    @NotNull
    @Column(name = "TRAVELLER_CAPACITY")
    private int travellerCapacity;

    @Column(name = "ADDITIONAL_CAPACITY")
    private int additionalCapacity;

    @NotNull
    @Column(name = "DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    @Column(name = "DISPLAY_START_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date displayStartDate;

    @Column(name = "DISPLAY_END_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date displayEndDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Double getPricePerAdult() {
        return pricePerAdult;
    }

    public void setPricePerAdult(Double pricePerAdult) {
        this.pricePerAdult = pricePerAdult;
    }

    public Double getTac() {
        return tac;
    }

    public void setTac(Double tac) {
        this.tac = tac;
    }

    public List<City> getTargetCities() {
        return targetCities;
    }

    public void setTargetCities(List<City> targetCities) {
        this.targetCities = targetCities;
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

    public int getTravellerCapacity() {
        return travellerCapacity;
    }

    public void setTravellerCapacity(int travellerCapacity) {
        this.travellerCapacity = travellerCapacity;
    }

    public int getAdditionalCapacity() {
        return additionalCapacity;
    }

    public void setAdditionalCapacity(int additionalCapacity) {
        this.additionalCapacity = additionalCapacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDisplayStartDate() {
        return displayStartDate;
    }

    public void setDisplayStartDate(Date displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    public Date getDisplayEndDate() {
        return displayEndDate;
    }

    public void setDisplayEndDate(Date displayEndDate) {
        this.displayEndDate = displayEndDate;
    }

    @Override
    public String toString() {
        return "TripInstance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", displayStartDate=" + displayStartDate +
                ", displayEndDate=" + displayEndDate +
                '}';
    }
}
