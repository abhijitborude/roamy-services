package com.roamy.service.notification.dto;

/**
 * Created by Abhijit on 2/7/2016.
 */
public class TripNotificationDto {

    private Long reservationId;

    private String name;

    private String date;

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

    @Override
    public String toString() {
        return "TripNotificationDto{" +
                "reservationId=" + reservationId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
