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
@Table(name = "TRIP_INSTANCE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class TripInstance extends AbstractEntity {

    public abstract String getType();

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    protected String name;

    @Column(name = "DESCRIPTION", length = DbConstants.MEDIUM_TEXT)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    @JsonIgnore
    protected Trip trip;

    @Column(name = "ADDITIONAL_DESCRIPTION", length = DbConstants.LONG_TEXT)
    protected String additionalDescription;

    @Column(name = "THRILL_METER")
    protected int thrillMeter;

    @Column(name = "PRICE_PER_ADULT")
    protected Double pricePerAdult;

    @Column(name = "TAC")
    protected Double tac;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TRIP_INSTANCE_ID")
    private List<TripInstanceOption> options;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_INSTANCE_CITY",
            joinColumns = {@JoinColumn(name = "TRIP_INSTANCE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CITY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    protected List<City> targetCities;

    @Column(name = "TRAVELLER_CAPACITY")
    protected Integer travellerCapacity;

    @NotNull
    @Column(name = "DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date date;

    @Column(name = "DISPLAY_START_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date displayStartDate;

    @Column(name = "DISPLAY_END_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date displayEndDate;

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

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public int getThrillMeter() {
        return thrillMeter;
    }

    public void setThrillMeter(int thrillMeter) {
        this.thrillMeter = thrillMeter;
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

    public List<TripInstanceOption> getOptions() {
        return options;
    }

    public void setOptions(List<TripInstanceOption> options) {
        this.options = options;
    }

    public List<City> getTargetCities() {
        return targetCities;
    }

    public void setTargetCities(List<City> targetCities) {
        this.targetCities = targetCities;
    }

    public Integer getTravellerCapacity() {
        return travellerCapacity;
    }

    public void setTravellerCapacity(Integer travellerCapacity) {
        this.travellerCapacity = travellerCapacity;
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
