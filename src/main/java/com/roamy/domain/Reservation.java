package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "RESERVATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Reservation extends AbstractEntity {

    public abstract String getType();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "RESERVATION_TRIP_INSTANCE",
            joinColumns = {@JoinColumn(name = "RESERVATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TRIP_INSTANCE_ID")})
    @OptimisticLock(excluded = true)
    protected List<TripInstance> tripInstances;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @NotNull
    @Column(name = "START_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date startDate;

    @NotNull
    @Column(name = "AMOUNT")
    protected Double amount;

    @Transient
    protected Double amountToPay;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    protected String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    protected String email;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    protected List<ReservationPayment> payments;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    protected List<ReservationTripOption> tripOptions;

    public int getNumberOfRomies() {
        int numberOfRomies = 0;
        for (ReservationTripOption option : tripOptions) {
            numberOfRomies += option.getCount();
        }
        return numberOfRomies;
    }

    public List<TripInstance> getTripInstances() {
        return tripInstances;
    }

    public void setTripInstances(List<TripInstance> tripInstances) {
        this.tripInstances = tripInstances;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
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
        payment.setReservation(this);
    }

    public List<ReservationTripOption> getTripOptions() {
        return tripOptions;
    }

    public void setTripOptions(List<ReservationTripOption> tripOptions) {
        this.tripOptions = tripOptions;
    }

    public void addTripOption(ReservationTripOption tripOption) {
        if (this.tripOptions == null) {
            this.tripOptions = new ArrayList<>();
        }

        this.tripOptions.add(tripOption);
        tripOption.setReservation(this);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", tripInstances=" + tripInstances +
                ", user=" + user +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
