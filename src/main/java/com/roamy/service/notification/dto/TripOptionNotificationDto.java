package com.roamy.service.notification.dto;

/**
 * Created by Abhijit on 4/18/2016.
 */
public class TripOptionNotificationDto {

    private String type;

    protected boolean ageBasedPricing;

    protected String price;
    protected String count;
    private String totalCost;

    protected String adultPrice;
    protected String adultCount;
    protected String adultTotalCost;

    protected String seniorPrice;
    protected String seniorCount;
    protected String seniorTotalCost;

    protected String childPrice;
    protected String childCount;
    protected String childTotalCost;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAgeBasedPricing() {
        return ageBasedPricing;
    }

    public void setAgeBasedPricing(boolean ageBasedPricing) {
        this.ageBasedPricing = ageBasedPricing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(String adultCount) {
        this.adultCount = adultCount;
    }

    public String getAdultTotalCost() {
        return adultTotalCost;
    }

    public void setAdultTotalCost(String adultTotalCost) {
        this.adultTotalCost = adultTotalCost;
    }

    public String getSeniorPrice() {
        return seniorPrice;
    }

    public void setSeniorPrice(String seniorPrice) {
        this.seniorPrice = seniorPrice;
    }

    public String getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(String seniorCount) {
        this.seniorCount = seniorCount;
    }

    public String getSeniorTotalCost() {
        return seniorTotalCost;
    }

    public void setSeniorTotalCost(String seniorTotalCost) {
        this.seniorTotalCost = seniorTotalCost;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public String getChildTotalCost() {
        return childTotalCost;
    }

    public void setChildTotalCost(String childTotalCost) {
        this.childTotalCost = childTotalCost;
    }
}
