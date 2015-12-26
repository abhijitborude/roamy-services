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
@Table(name = "ALERT", schema = "ROAMY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class Alert extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    protected User user;

    @NotNull
    @Column(name = "TITLE", length = DbConstants.SHORT_TEXT)
    protected String title;

    @NotNull
    @Column(name = "MESSAGE", length = DbConstants.MEDIUM_TEXT)
    protected String message;

    @Column(name = "READ_STATUS")
    protected boolean readStatus;

    @NotNull
    @Column(name = "EXPIRY_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date expiryDate;

    public abstract String getType();

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
        return "Alert{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", readStatus=" + readStatus +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
