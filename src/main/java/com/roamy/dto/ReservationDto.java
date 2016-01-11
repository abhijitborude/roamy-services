package com.roamy.dto;

/**
 * Created by Abhijit on 12/13/2015.
 */
public class ReservationDto {

    private Long userId;

    private Long tripInstanceId;

    private int numberOfTravellers;

    private Double amount;

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

    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
                ", tripInstanceId='" + tripInstanceId + '\'' +
                ", numberOfTravellers=" + numberOfTravellers +
                ", amount=" + amount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
