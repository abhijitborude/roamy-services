package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "RESERVATION", schema = "ROAMY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Reservation extends AbstractEntity {

    public abstract String getType();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "RESERVATION_TRIP_INSTANCE", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "RESERVATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TRIP_INSTANCE_ID")})
    protected List<TripInstance> tripInstance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @NotNull
    @Column(name = "NUMBER_OF_ROAMIES")
    protected int numberOfRoamies;

    @NotNull
    @Column(name = "START_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date startDate;

    @NotNull
    @Column(name = "AMOUNT")
    protected Double amount;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    protected String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    protected String email;

    @OneToMany
    @JoinColumn(name = "RESERVATION_ID")
    protected List<ReservationPayment> payments;

    @OneToMany
    @JoinColumn(name = "RESERVATION_ID")
    protected List<ReservationTripOption> tripOptions;

    public List<TripInstance> getTripInstance() {
        return tripInstance;
    }

    public void setTripInstance(List<TripInstance> tripInstance) {
        this.tripInstance = tripInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfRoamies() {
        return numberOfRoamies;
    }

    public void setNumberOfRoamies(int numberOfRoamies) {
        this.numberOfRoamies = numberOfRoamies;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReservationPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<ReservationPayment> payments) {
        this.payments = payments;
    }

    public void addPayment(ReservationPayment payment) {
        if (this.payments == null) {
            this.payments = new ArrayList<>();
        }

        this.payments.add(payment);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "tripInstance=" + tripInstance +
                ", user=" + user +
                ", numberOfRoamies=" + numberOfRoamies +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
