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

        RoamyUtils.addAuditPropertiesForCreateEntity(city, "test");
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

        RoamyUtils.addAuditPropertiesForCreateEntity(category, "test");
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

        RoamyUtils.addAuditPropertiesForCreateEntity(user, "test");
        return user;
    }

    public static void setPropertiesForTrip(Trip trip, String code, int thrillMeter, Double pricePerAdult, Status status) {
        trip.setCode(code);
        trip.setName(code);
        trip.setDescription(code);
        trip.setThrillMeter(thrillMeter);
        trip.setPricePerAdult(pricePerAdult);
        trip.setStatus(status);

        RoamyUtils.addAuditPropertiesForCreateEntity(trip, "test");
    }

    public static void setPropertiesForTripInstance(TripInstance tripInstance, String name, int thrillMeter, Double pricePerAdult, Status status) {
        tripInstance.setName(name);
        tripInstance.setDescription(name);
        tripInstance.setThrillMeter(thrillMeter);
        tripInstance.setPricePerAdult(pricePerAdult);
        tripInstance.setStatus(status);

        RoamyUtils.addAuditPropertiesForCreateEntity(tripInstance, "test");
    }

    public static TripInstanceOption createTripInstanceOption(String name, Double amount) {
        TripInstanceOption option = new TripInstanceOption();
        option.setName(name);
        option.setPrice(amount);
        option.setStatus(Status.Active);
        RoamyUtils.addAuditPropertiesForCreateEntity(option, "test");
        return option;
    }

    public static void addPropertiesToReservation(Reservation reservation, Double amount, Date startDate, String email, String phoneNumber, Status status) {
        reservation.setAmount(amount);
        reservation.setStartDate(startDate);
        reservation.setEmail(email);
        reservation.setPhoneNumber(email);
        reservation.setStatus(status);

        RoamyUtils.addAuditPropertiesForCreateEntity(reservation, "test");
    }

    public static ReservationPayment createReservationPayment(Double amount, PaymentType type) {
        ReservationPayment payment = new ReservationPayment();
        payment.setAmount(amount);
        payment.setType(type);

        RoamyUtils.addAuditPropertiesForCreateEntity(payment, "test");
        return payment;
    }
}
