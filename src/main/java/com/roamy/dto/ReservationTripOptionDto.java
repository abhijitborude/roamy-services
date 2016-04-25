package com.roamy.dto;

/**
 * Created by Abhijit on 2/23/16.
 */
public class ReservationTripOptionDto {

    private Long tripInstanceOptionId;

    private Integer count;

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

    @Override
    public String toString() {
        return "ReservationTripOptionDto{" +
                "tripInstanceOptionId=" + tripInstanceOptionId +
                ", count=" + count +
                '}';
    }
}
