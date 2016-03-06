package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "PAYMENT")
public class Payment extends AbstractEntity {

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;

    @NotNull
    @Column(name = "DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                "amount=" + amount +
                ", date=" + date +
                '}';
    }
}
