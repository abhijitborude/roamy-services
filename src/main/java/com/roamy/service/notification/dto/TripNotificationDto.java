package com.roamy.service.notification.dto;

import java.util.List;

/**
 * Created by Abhijit on 2/7/2016.
 */
public class TripNotificationDto {

    private Long reservationId;

    private String name;

    private String date;

    private String coverPicture;

    private String user;

    private List<TripOptionNotificationDto> options;

    private String totalCost;

    private String meetingPoints;

    private String thingsToCarry;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<TripOptionNotificationDto> getOptions() {
        return options;
    }

    public void setOptions(List<TripOptionNotificationDto> options) {
        this.options = options;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getMeetingPoints() {
        return meetingPoints;
    }

    public void setMeetingPoints(String meetingPoints) {
        this.meetingPoints = meetingPoints;
    }

    public String getThingsToCarry() {
        return thingsToCarry;
    }

    public void setThingsToCarry(String thingsToCarry) {
        this.thingsToCarry = thingsToCarry;
    }

    @Override
    public String toString() {
        return "TripNotificationDto{" +
                "reservationId=" + reservationId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
