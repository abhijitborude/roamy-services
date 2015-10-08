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
@Table(name = "TRIP", schema = "ROAMY")
public class Trip extends AbstractEntity {

    @Column(name = "NAME", nullable = false, length = DbConstants.MEDIUM_TEXT)
    private String name;

    @Column(name = "CODE", nullable = false, length = DbConstants.MEDIUM_TEXT)
    private String code;

    @Column(name = "NUMBER_OF_DAYS")
    private int numberOfDays;

    @Column(name = "DESCRIPTION", length = DbConstants.LONG_TEXT)
    private String description;

    @Column(name = "DIFFICULTY_LEVEL")
    private int difficultyLevel;

    @Column(name = "PRICE_PER_ADULT")
    private Double pricePerAdult;

    @Column(name = "TAC")
    private Double tac;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_CITY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CITY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    private List<City> targetCities;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_CATEGORY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Category> category;

    @Column(name = "ITINERARY", length = DbConstants.LONG_TEXT)
    private String itinerary;

    @Column(name = "INCLUSIONS", length = DbConstants.LONG_TEXT)
    private String inclusions;

    @Column(name = "EXCLUSIONS", length = DbConstants.LONG_TEXT)
    private String exclusions;

    @Column(name = "MEETING_POINTS", length = DbConstants.LONG_TEXT)
    private String meetingPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Double getPricePerAdult() {
        return pricePerAdult;
    }

    public void setPricePerAdult(Double pricePerAdult) {
        this.pricePerAdult = pricePerAdult;
    }

    public Double getTac() {
        return tac;
    }

    public void setTac(Double tac) {
        this.tac = tac;
    }

    public List<City> getTargetCities() {
        return targetCities;
    }

    public void setTargetCities(List<City> targetCities) {
        this.targetCities = targetCities;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getMeetingPoints() {
        return meetingPoints;
    }

    public void setMeetingPoints(String meetingPoints) {
        this.meetingPoints = meetingPoints;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
