package in.roamy.domain;

import in.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "USER", schema = "ROAMY")
public class User extends AbstractEntity {

    @NotNull
    @Column(name = "TYPE", length = DbConstants.SHORT_TEXT)
    private String type;

    @NotNull
    @Column(name = "EMAIL", length = DbConstants.SHORT_TEXT)
    private String email;

    @Column(name = "PASS", length = DbConstants.SHORT_TEXT)
    private String password;

    @Column(name = "FNAME", length = DbConstants.SHORT_TEXT)
    private String firstName;

    @Column(name = "LNAME", length = DbConstants.SHORT_TEXT)
    private String lastName;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "ADDRESS", length = DbConstants.SHORT_TEXT)
    private String address;

    @Column(name = "CITY", length = DbConstants.SHORT_TEXT)
    private String city;

    @Column(name = "COUNTRY", length = DbConstants.SHORT_TEXT)
    private String country;

    @Column(name = "PIN", length = DbConstants.SHORT_TEXT)
    private String pinCode;

    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @Column(name = "V_CODE", length = DbConstants.SHORT_TEXT)
    private String verificationCode;

    @Column(name = "V_EXPIRY")
    private Date verificationCodeExpiry;

    @Column(name = "VERIFIED")
    private boolean isVerified;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "User{" +
                "type='" + type + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
