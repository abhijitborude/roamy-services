package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 1/30/2016.
 */
@Entity
@Table(name = "TRIP_OPTION", schema = "ROAMY")
public class TripOption extends AbstractEntity {

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    protected String name;

    @Column(name = "DESCRIPTION", length = DbConstants.MEDIUM_TEXT)
    protected String description;

    @Column(name = "PRICE")
    protected Double price;

    @Column(name = "CAPACITY")
    protected Double capacity;

    @NotNull
    @Column(name = "AGE_BASED_PRICING")
    protected boolean ageBasedPricing;

    @Column(name = "ADULT_PRICE")
    protected Double adultPrice;

    @Column(name = "SENIOR_PRICE")
    protected Double seniorPrice;

    @Column(name = "CHILD_PRICE")
    protected Double childPrice;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public boolean isAgeBasedPricing() {
        return ageBasedPricing;
    }

    public void setAgeBasedPricing(boolean ageBasedPricing) {
        this.ageBasedPricing = ageBasedPricing;
    }

    public Double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(Double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Double getSeniorPrice() {
        return seniorPrice;
    }

    public void setSeniorPrice(Double seniorPrice) {
        this.seniorPrice = seniorPrice;
    }

    public Double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Double childPrice) {
        this.childPrice = childPrice;
    }

    @Override
    public String toString() {
        return "TripOption{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", ageBasedPricing=" + ageBasedPricing +
                ", adultPrice=" + adultPrice +
                ", seniorPrice=" + seniorPrice +
                ", childPrice=" + childPrice +
                '}';
    }
}
