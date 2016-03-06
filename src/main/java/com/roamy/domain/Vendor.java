package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "VENDOR")
public class Vendor extends CitableEntity {

    @Column(name = "NOTES", length = DbConstants.VERY_LONG_TEXT)
    private String notes;

    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String email;

    @Column(name = "ADDITIONAL_EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String additionalEmail;

    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @Column(name = "COMMISSION")
    private Double commission;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VendorAccount> account;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public List<VendorAccount> getAccount() {
        return account;
    }

    public void setAccount(List<VendorAccount> account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
