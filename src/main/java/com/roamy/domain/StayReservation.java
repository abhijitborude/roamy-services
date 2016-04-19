package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("STAY")
public class StayReservation extends Reservation {

    @NotNull
    @Column(name = "END_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date endDate;

    @Override
    public String getType() {
        return "STAY";
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "StayReservation{" +
                "tripInstances=" + tripInstances +
                ", user=" + user +
                ", numberOfRoamies=" + getNumberOfRoamies() +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
