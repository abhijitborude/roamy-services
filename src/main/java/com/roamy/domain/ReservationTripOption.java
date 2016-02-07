package com.roamy.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 1/31/2016.
 */
@Entity
@Table(name = "RESERVATION_TRIP_OPTION", schema = "ROAMY")
public class ReservationTripOption extends AbstractEntity {

    @Column(name = "COUNT")
    protected int count;

    @NotNull
    @Column(name = "AGE_BASED_PRICING")
    protected boolean ageBasedPricing;

    @Column(name = "ADULT_COUNT")
    protected int adultCount;

    @Column(name = "SENIOR_COUNT")
    protected int seniorCount;

    @Column(name = "CHILD_COUNT")
    protected int childCount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRIP_INSTANCE_OPTION_ID")
    protected TripInstanceOption tripInstanceOption;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isAgeBasedPricing() {
        return ageBasedPricing;
    }

    public void setAgeBasedPricing(boolean ageBasedPricing) {
        this.ageBasedPricing = ageBasedPricing;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(int seniorCount) {
        this.seniorCount = seniorCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public TripInstanceOption getTripInstanceOption() {
        return tripInstanceOption;
    }

    public void setTripInstanceOption(TripInstanceOption tripInstanceOption) {
        this.tripInstanceOption = tripInstanceOption;
    }

    @Override
    public String toString() {
        return "ReservationTripOption{" +
                "count=" + count +
                ", ageBasedPricing=" + ageBasedPricing +
                ", adultCount=" + adultCount +
                ", seniorCount=" + seniorCount +
                ", childCount=" + childCount +
                ", tripInstanceOption=" + tripInstanceOption +
                '}';
    }
}
