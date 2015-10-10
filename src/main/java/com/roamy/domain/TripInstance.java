package com.roamy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "TRIP_INSTANCE", schema = "ROAMY")
public class TripInstance extends AbstractEntity {

    @NotNull
    @Column(name = "DATE")
    private Date date;

    @NotNull
    @Column(name = "TRAVELLER_CAPACITY")
    private int travellerCapacity;

    @Column(name = "ADDITIONAL_CAPACITY")
    private int additionalCapacity;

    @Column(name = "DISPLAY_START_DATE")
    private Date displayStartDate;

    @Column(name = "DISPLAY_END_DATE")
    private Date displayEndDate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                "date=" + date +
                ", displayStartDate=" + displayStartDate +
                ", displayEndDate=" + displayEndDate +
                '}';
    }
}
