package com.roamy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roamy.util.DbConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "TRIP", schema = "ROAMY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Trip extends CitableEntity {

    public abstract String getType();

    @Column(name = "ADDITIONAL_DESCRIPTION", length = DbConstants.LONG_TEXT)
    protected String additionalDescription;

    @Column(name = "THRILL_METER")
    protected int thrillMeter;

    @Column(name = "PRICE_PER_ADULT")
    private Double pricePerAdult;

    @Column(name = "TAC")
    protected Double tac;

    @OneToMany
    @JoinColumn(name = "TRIP_ID")
    private List<TripOption> options;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_CITY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CITY_ID")})
    protected List<City> targetCities;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TRIP_CATEGORY", schema = "ROAMY",
            joinColumns = {@JoinColumn(name = "TRIP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    protected List<Category> categories;

    @ElementCollection
    @CollectionTable(
            schema = "ROAMY",
            name="TRIP_IMAGE",
            joinColumns=@JoinColumn(name="TRIP_ID")
    )
    protected List<TripImage> images;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<TripInstance> instances = new ArrayList<TripInstance>();

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public int getThrillMeter() {
        return thrillMeter;
    }

    public void setThrillMeter(int thrillMeter) {
        this.thrillMeter = thrillMeter;
    }

    public Double getTac() {
        return tac;
    }

    public void setTac(Double tac) {
        this.tac = tac;
    }

    public List<TripOption> getOptions() {
        return options;
    }

    public void setOptions(List<TripOption> options) {
        this.options = options;
    }

    public List<City> getTargetCities() {
        return targetCities;
    }

    public void setTargetCities(List<City> targetCities) {
        this.targetCities = targetCities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<TripImage> getImages() {
        return images;
    }

    public void setImages(List<TripImage> images) {
        this.images = images;
    }

    public List<TripInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<TripInstance> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return this.code.equals(trip.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
