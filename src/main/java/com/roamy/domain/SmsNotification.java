package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 12/8/2015.
 */
@Entity
@Table(name = "SMS_NOTIFICATION")
public class SmsNotification extends AbstractEntity {

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @NotNull
    @Column(name = "MESSAGE", length = DbConstants.LONG_TEXT)
    private String message;

    @Column(name = "ERROR_CODE", length = DbConstants.SHORT_TEXT)
    private String errorCode;

    @Column(name = "ERROR_DESCRIPTION", length = DbConstants.LONG_TEXT)
    private String errorDescription;

    public SmsNotification() {
    }

    public SmsNotification(String phoneNumber, String message, Status status) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "SmsNotification{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
