package com.roamy.dto;

/**
 * Created by Abhijit on 2/23/16.
 */
public class ReservationTripOptionDto {

    private Long tripInstanceOptionId;

    private Integer count;

    private Integer adultCount;

    private Integer seniorCount;

    private Integer childCount;

    public Long getTripInstanceOptionId() {
        return tripInstanceOptionId;
    }

    public void setTripInstanceOptionId(Long tripInstanceOptionId) {
        this.tripInstanceOptionId = tripInstanceOptionId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public Integer getSeniorCount() {
        return seniorCount;
    }

    public void setSeniorCount(Integer seniorCount) {
        this.seniorCount = seniorCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    @Override
    public String toString() {
        return "ReservationTripOptionDto{" +
                "tripInstanceOptionId=" + tripInstanceOptionId +
                ", count=" + count +
                ", adultCount=" + adultCount +
                ", seniorCount=" + seniorCount +
                ", childCount=" + childCount +
                '}';
    }
}
