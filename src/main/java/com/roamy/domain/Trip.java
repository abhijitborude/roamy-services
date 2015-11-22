package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "TRIP", schema = "ROAMY")
public class Trip extends CitableEntity {

    @Column(name = "NUMBER_OF_DAYS")
    private int numberOfDays;

    @Column(name = "ADDITIONAL_DESCRIPTION", length = DbConstants.LONG_TEXT)
    private String additionalDescription;

    @Column(name = "DIFFICULTY_LEVEL")
    private int difficultyLevel;

    @Column(name = "DEFAULT_PRICE_PER_ADULT")
    private Double defaultPricePerAdult;

    @Column(name = "TAC")
    private Double tac;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TargetCity> targetCities = new ArrayList<TargetCity>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_CATEGORY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Category> categories;

    @Column(name = "ITINERARY", length = DbConstants.LONG_TEXT)
    private String itinerary;

    @Column(name = "INCLUSIONS", length = DbConstants.LONG_TEXT)
    private String inclusions;

    @Column(name = "EXCLUSIONS", length = DbConstants.LONG_TEXT)
    private String exclusions;

    @Column(name = "MEETING_POINTS", length = DbConstants.LONG_TEXT)
    private String meetingPoints;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TripInstance> instances = new ArrayList<TripInstance>();

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

    public Double getDefaultPricePerAdult() {
        return defaultPricePerAdult;
    }

    public void setDefaultPricePerAdult(Double defaultPricePerAdult) {
        this.defaultPricePerAdult = defaultPricePerAdult;
    }

    public Double getTac() {
        return tac;
    }

    public void setTac(Double tac) {
        this.tac = tac;
    }

    public List<TargetCity> getTargetCities() {
        return targetCities;
    }

    public void setTargetCities(List<TargetCity> targetCities) {
        this.targetCities = targetCities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public List<TripInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<TripInstance> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
