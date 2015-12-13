package com.roamy.dto;

/**
 * Created by Abhijit on 12/13/2015.
 */
public class FavoriteTripAction {

    public static final String ADD_ACTION = "add";
    public static final String REMOVE_ACTION = "remove";

    private String tripCode;

    // action should be add, remove
    private String action;

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "FavoriteTripAction{" +
                "tripCode='" + tripCode + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
