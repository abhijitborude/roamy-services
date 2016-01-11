package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "RESERVATION", schema = "ROAMY")
public class Reservation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRIP_INSTANCE_ID")
    private TripInstance tripInstance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "NUMBER_OF_TRAVELLERS")
    private int numberOfTravellers;

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String email;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReservationPayment> payments = new ArrayList<>();

    public TripInstance getTripInstance() {
        return tripInstance;
    }

    public void setTripInstance(TripInstance tripInstance) {
        this.tripInstance = tripInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
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
        payment.setReservation(this);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "tripInstance=" + tripInstance +
                ", user=" + user +
                ", numberOfTravellers=" + numberOfTravellers +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
