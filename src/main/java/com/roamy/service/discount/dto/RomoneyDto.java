package com.roamy.service.discount.dto;

/**
 * Created by Abhijit on 5/1/2016.
 */
public class RomoneyDto {

    private Long userId;

    private String tripCode;

    private Double bookingAmount;

    private Double userWalletBalance;

    private Double romoneyAmountToApply;

    public RomoneyDto() {
    }

    public RomoneyDto(Long userId, String tripCode, Double bookingAmount) {
        this.userId = userId;
        this.tripCode = tripCode;
        this.bookingAmount = bookingAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public Double getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(Double bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public Double getUserWalletBalance() {
        return userWalletBalance;
    }

    public void setUserWalletBalance(Double userWalletBalance) {
        this.userWalletBalance = userWalletBalance;
    }

    public Double getRomoneyAmountToApply() {
        return romoneyAmountToApply;
    }

    public void setRomoneyAmountToApply(Double romoneyAmountToApply) {
        this.romoneyAmountToApply = romoneyAmountToApply;
    }

    @Override
    public String toString() {
        return "RomoneyDto{" +
                "userId=" + userId +
                ", tripCode='" + tripCode + '\'' +
                ", bookingAmount=" + bookingAmount +
                ", userWalletBalance=" + userWalletBalance +
                ", romoneyAmountToApply=" + romoneyAmountToApply +
                '}';
    }
}
