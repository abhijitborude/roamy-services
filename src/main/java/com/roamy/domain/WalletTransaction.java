package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 5/1/2016.
 */
@Entity
@Table(name = "WALLET_TRANSACTION")
public class WalletTransaction extends AbstractEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "COMMENT", length = DbConstants.MEDIUM_TEXT)
    private String comment;

    public WalletTransaction() {
    }

    public WalletTransaction(User user, Double amount, String comment) {
        this.user = user;
        this.amount = amount;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "WalletTransaction{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
