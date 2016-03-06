package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 2/2/2016.
 */
@Entity
@Table(name = "EMAIL_NOTIFICATION")
public class EmailNotification extends AbstractEntity {

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String email;

    @Column(name = "SUBJECT_PARAMS", length = DbConstants.LONG_TEXT)
    private String subjectParams;

    @Column(name = "PARAMS", length = DbConstants.ULTRA_LONG_TEXT)
    private String params;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL_TEMPLATE_ID")
    private EmailTemplate emailTemplate;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "RESERVATION_ID")
    private Long reservationId;

    @Column(name = "SENT_ON")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date sentOn;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectParams() {
        return subjectParams;
    }

    public void setSubjectParams(String subjectParams) {
        this.subjectParams = subjectParams;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Date getSentOn() {
        return sentOn;
    }

    public void setSentOn(Date sentOn) {
        this.sentOn = sentOn;
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
                "email='" + email + '\'' +
                ", emailTemplate=" + emailTemplate.getCode() +
                ", sentOn=" + sentOn +
                ", createdOn=" + createdOn +
                ", status=" + status +
                '}';
    }
}
