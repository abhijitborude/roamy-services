package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.*;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "VENDOR_ACCOUNT")
public class VendorAccount extends AbstractEntity {

    @Column(name = "NAME", nullable = false, length = DbConstants.MEDIUM_TEXT)
    private String name;

    @Column(name = "BANK_NAME", nullable = false, length = DbConstants.MEDIUM_TEXT)
    private String bankName;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = DbConstants.SHORT_TEXT)
    private String accountNumber;

    @Column(name = "IFSC_CODE", nullable = false, length = DbConstants.SHORT_TEXT)
    private String ifscCode;

    @Column(name = "PAN", length = DbConstants.SHORT_TEXT)
    private String pan;

    @Column(name = "TAN", length = DbConstants.SHORT_TEXT)
    private String tan;

    @Column(name = "SERVICE_TAX_NUMBER", length = DbConstants.SHORT_TEXT)
    private String serviceTaxNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDOR_ID")
    private Vendor vendor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getServiceTaxNumber() {
        return serviceTaxNumber;
    }

    public void setServiceTaxNumber(String serviceTaxNumber) {
        this.serviceTaxNumber = serviceTaxNumber;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "VendorAccount{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
