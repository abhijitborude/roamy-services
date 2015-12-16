package com.roamy.domain;

/**
 * Created by Abhijit on 7/2/2015.
 */
public enum Status {

    Active,
    Inactive,
    Pending,
    Success,
    Failed,
    Cancelled;

    public static Status findByText(String text) {
        if (text != null) {
            for (Status status : Status.values()) {
                if (status.name().equalsIgnoreCase(text.toLowerCase())) {
                    return status;
                }
            }
        }
        return null;
    }
}
