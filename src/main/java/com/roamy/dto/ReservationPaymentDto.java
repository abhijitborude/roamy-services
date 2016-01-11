package com.roamy.dto;

/**
 * Created by Abhijit on 1/10/2016.
 */
public class ReservationPaymentDto {

    private String type;

    private Double amount;

    private String transactionId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
        return "ReservationPaymentDto{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
