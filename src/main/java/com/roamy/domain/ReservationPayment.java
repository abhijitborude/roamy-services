package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 1/10/2016.
 */
@Entity
@Table(name = "RESERVATION_PAYMENT", schema = "ROAMY")
public class ReservationPayment extends AbstractEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = DbConstants.SHORT_TEXT)
    private PaymentType type;

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "TRANSACTION_ID", length = DbConstants.SHORT_TEXT)
    private String transactionId;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "ReservationPayment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
