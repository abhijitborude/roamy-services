package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 12/22/2015.
 */
@Entity
@Table(name = "ALERT_NOTIFICATION", schema = "ROAMY")
public class AlertNotification extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "TITLE", length = DbConstants.SHORT_TEXT)
    private String title;

    @NotNull
    @Column(name = "MESSAGE", length = DbConstants.MEDIUM_TEXT)
    private String message;

    @NotNull
    @Column(name = "INSTRUCTIONS", length = DbConstants.MEDIUM_TEXT)
    private String instructions;

    @Column(name = "READ_STATUS")
    private boolean readStatus;

    @NotNull
    @Column(name = "EXPIRY_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date expiryDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "AlertNotification{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", instructions='" + instructions + '\'' +
                ", readStatus=" + readStatus +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
