package com.roamy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
