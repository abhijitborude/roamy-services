package com.roamy.util;

import com.roamy.domain.*;

import java.util.Date;

/**
 * Created by Abhijit on 4/10/2016.
 */
public class DomainObjectUtil {

    public static City createCity(String code, String name) {
        City city = new City();
        city.setCode(code);
        city.setName(name);
        city.setDescription("Test Description");
        city.setStatus(Status.Active);
        city.setCreatedBy("test");
        city.setCreatedOn(new Date());
        city.setLastModifiedBy("test");
        city.setLastModifiedOn(new Date());
        return city;
    }

    public static Category createCategory(String code, String name) {
        Category category = new Category();
        category.setCode(code);
        category.setName(name);
        category.setDescription("Test Description");
        category.setImageCaption("Test Caption");
        category.setImageUrl("http://test");
        category.setStatus(Status.Active);
        category.setCreatedBy("test");
        category.setLastModifiedBy("test");
        return category;
    }

    public static User getUser(String phoneNumber, String email, String fname, String lname) {
        User user = new User();
        user.setType(UserType.ROLE_ROAMY);
        user.setAccountType(AccountType.Phone);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setStatus(Status.Active);
        user.setCreatedBy("test");
        user.setCreatedOn(new Date());
        user.setLastModifiedBy("test");
        user.setLastModifiedOn(new Date());
        return user;
    }

    public static void setPropertiesForTrip(Trip trip, String code, int thrillMeter, Double pricePerAdult, Status status) {
        trip.setCode(code);
        trip.setName(code);
        trip.setDescription(code);
        trip.setThrillMeter(thrillMeter);
        trip.setPricePerAdult(pricePerAdult);
        trip.setStatus(status);
        trip.setCreatedBy("test");
        trip.setLastModifiedBy("test");
    }

    public static void setPropertiesForTripInstance(TripInstance tripInstance, String name, int thrillMeter, Double pricePerAdult, Status status) {
        tripInstance.setName(name);
        tripInstance.setDescription(name);
        tripInstance.setThrillMeter(thrillMeter);
        tripInstance.setPricePerAdult(pricePerAdult);
        tripInstance.setStatus(status);
        tripInstance.setCreatedBy("test");
        tripInstance.setLastModifiedBy("test");
    }
}
