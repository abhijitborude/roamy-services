package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Abhijit on 12/25/15.
 */
@Entity
@DiscriminatorValue(value = "PROMO")
public class PromoAlert extends Alert {

    @Column(name = "PROMO_CODE", length = DbConstants.SHORT_TEXT)
    private String promoCode;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public String getType() {
        return "promo";
    }

    @Override
    public String toString() {
        return "PromoAlert{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", promoCode='" + promoCode + '\'' +
                ", readStatus=" + readStatus +
                ", expiryDate=" + expiryDate +
                '}';
    }

}
