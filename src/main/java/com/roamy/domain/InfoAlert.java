package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 12/25/15.
 */
@Entity
@DiscriminatorValue(value = "INFO")
public class InfoAlert extends Alert {

    @Override
    public String getType() {
        return "info";
    }

    @Override
    public String toString() {
        return "InfoAlert{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", readStatus=" + readStatus +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
