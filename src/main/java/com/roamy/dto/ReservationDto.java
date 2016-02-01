package com.roamy.dto;

import java.util.Map;

/**
 * Created by Abhijit on 12/13/2015.
 */
public class ReservationDto {

    private Long userId;

    private Long tripInstanceId;

    private Map<Long, Integer> tripOptionReserations;

    private boolean useRomoney = false;

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

    public Map<Long, Integer> getTripOptionReserations() {
        return tripOptionReserations;
    }

    public void setTripOptionReserations(Map<Long, Integer> tripOptionReserations) {
        this.tripOptionReserations = tripOptionReserations;
    }

    public boolean isUseRomoney() {
        return useRomoney;
    }

    public void setUseRomoney(boolean useRomoney) {
        this.useRomoney = useRomoney;
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
                ", tripOptionReserations=" + tripOptionReserations +
                ", useRomoney=" + useRomoney +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
