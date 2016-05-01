package com.roamy.dto;

import java.util.List;

/**
 * Created by Abhijit on 12/13/2015.
 */
public class ReservationDto {

    private Long userId;

    private Long tripInstanceId;

    private List<ReservationTripOptionDto> reservationTripOptions;

    private boolean useRomoney = false;

    private Double romoneyAmount;

    private String phoneNumber;

    private String email;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTripInstanceId() {
        return tripInstanceId;
    }

    public void setTripInstanceId(Long tripInstanceId) {
        this.tripInstanceId = tripInstanceId;
    }

    public List<ReservationTripOptionDto> getReservationTripOptions() {
        return reservationTripOptions;
    }

    public void setReservationTripOptions(List<ReservationTripOptionDto> reservationTripOptions) {
        this.reservationTripOptions = reservationTripOptions;
    }

    public boolean isUseRomoney() {
        return useRomoney;
    }

    public void setUseRomoney(boolean useRomoney) {
        this.useRomoney = useRomoney;
    }

    public Double getRomoneyAmount() {
        return romoneyAmount;
    }

    public void setRomoneyAmount(Double romoneyAmount) {
        this.romoneyAmount = romoneyAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "userId=" + userId +
                ", tripInstanceId=" + tripInstanceId +
                ", reservationTripOptions=" + reservationTripOptions +
                ", useRomoney=" + useRomoney +
                ", romoneyAmount=" + romoneyAmount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
