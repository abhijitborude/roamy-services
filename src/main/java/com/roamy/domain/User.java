package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateDeserializer;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "USER", schema = "ROAMY")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = DbConstants.SHORT_TEXT)
    private AccountType type;

    @NotNull
    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.SHORT_TEXT)
    private String email;

    @Column(name = "PASS", length = DbConstants.SHORT_TEXT)
    private String password;

    @Column(name = "FNAME", length = DbConstants.SHORT_TEXT)
    private String firstName;

    @Column(name = "LNAME", length = DbConstants.SHORT_TEXT)
    private String lastName;

    @Column(name = "WALLET_BALANCE")
    private Double walletBalance;

    @Column(name = "PROFILE_IMAGE_ID", length = DbConstants.MEDIUM_TEXT)
    private String profileImageId;

    @Column(name = "PROFILE_IMAGE_URL", length = DbConstants.LONG_TEXT)
    private String profileImageUrl;

    @Column(name = "BIRTH_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date birthDate;

    @Column(name = "ADDRESS", length = DbConstants.SHORT_TEXT)
    private String address;

    @Column(name = "CITY", length = DbConstants.SHORT_TEXT)
    private String city;

    @Column(name = "COUNTRY", length = DbConstants.SHORT_TEXT)
    private String country;

    @Column(name = "PIN", length = DbConstants.SHORT_TEXT)
    private String pinCode;

    @Column(name = "VERIFICATION_CODE", length = DbConstants.SHORT_TEXT)
    //@JsonIgnore
    private String verificationCode;

    @Column(name = "VERIFICATION_EXPIRY")
    @JsonSerialize(using = CustomDateSerializer.class)
    //@JsonIgnore
    private Date verificationCodeExpiry;

    @Column(name = "VERIFIED")
    private boolean isVerified;

    @Column(name = "REFERRAL_CODE", length = DbConstants.SHORT_TEXT)
    private String referralCode;

    @Column(name = "DEVICE_ID", length = DbConstants.SHORT_TEXT)
    //@JsonIgnore
    private String deviceId;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getVerificationCodeExpiry() {
        return verificationCodeExpiry;
    }

    public void setVerificationCodeExpiry(Date verificationCodeExpiry) {
        this.verificationCodeExpiry = verificationCodeExpiry;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "User{" +
                "type='" + type + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
