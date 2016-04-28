package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@DiscriminatorValue("PACKAGE")
public class PackageReservation extends Reservation {
    
    @Override
    public String getType() {
        return "PACKAGE";
    }

    @Override
    public String toString() {
        return "PackageReservation{" +
                "id=" + id +
                ", tripInstances=" + tripInstances +
                ", user=" + user +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
