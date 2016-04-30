package com.roamy.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 1/31/2016.
 */
@Entity
@Table(name = "RESERVATION_TRIP_OPTION")
public class ReservationTripOption extends AbstractEntity {

    @Column(name = "COUNT")
    protected int count;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_ID")
    protected Reservation reservation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRIP_INSTANCE_OPTION_ID")
    protected TripInstanceOption tripInstanceOption;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
                ", tripInstanceOption=" + tripInstanceOption +
                '}';
    }
}
