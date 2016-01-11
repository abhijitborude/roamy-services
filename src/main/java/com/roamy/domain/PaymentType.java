package com.roamy.domain;

/**
 * Created by Abhijit on 7/2/2015.
 */
public enum PaymentType {

    Razorpay,
    Discount,
    Romoney;

    public static PaymentType findByText(String text) {
        if (text != null) {
            for (PaymentType status : PaymentType.values()) {
                if (status.name().equalsIgnoreCase(text.toLowerCase())) {
                    return status;
                }
            }
        }
        return null;
    }
}
