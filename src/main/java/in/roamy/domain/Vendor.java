package in.roamy.domain;

import in.roamy.util.DbConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "VENDOR", schema = "ROAMY")
public class Vendor extends AbstractEntity {

    @Column(name = "NAME", nullable = false, length = DbConstants.MEDIUM_TEXT)
    private String name;

    @Column(name = "NOTES", length = DbConstants.ULTRA_LONG_TEXT)
    private String notes;

    @Column(name = "EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String email;

    @Column(name = "ADDITIONAL_EMAIL", length = DbConstants.MEDIUM_TEXT)
    private String additionalEmail;

    @Column(name = "PHONE_NUMBER", length = DbConstants.SHORT_TEXT)
    private String phoneNumber;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VendorAccount> account;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "VENDOR_CITY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "VENDOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CITY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    private List<City> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<VendorAccount> getAccount() {
        return account;
    }

    public void setAccount(List<VendorAccount> account) {
        this.account = account;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
