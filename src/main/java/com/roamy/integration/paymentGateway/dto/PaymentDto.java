package com.roamy.integration.paymentGateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Abhijit on 1/11/2016.
 */
public class PaymentDto {

    private String id;

    private String entity;

    private long amount;

    private String currency;

    private String status;

    @JsonProperty("amount_refunded")
    private long amountRefunded;

    @JsonProperty("refund_status")
    private String refundStatus;

    private String email;

    private String contact;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("created_at")
    private long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(long amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "id='" + id + '\'' +
                ", entity='" + entity + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", amountRefunded=" + amountRefunded +
                ", refundStatus='" + refundStatus + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
