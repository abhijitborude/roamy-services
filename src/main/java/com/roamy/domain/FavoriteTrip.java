package com.roamy.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Abhijit on 11/20/2015.
 */
@Entity
@Table(name = "FAVORITE_TRIP", schema = "ROAMY")
public class FavoriteTrip extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    private Trip trip;

    public FavoriteTrip() {
        super();
    }

    public FavoriteTrip(User user, Trip trip) {
        super();
        this.user = user;
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "FavoriteTrip{" +
                "user=" + user +
                ", trip=" + trip +
                '}';
    }
}
