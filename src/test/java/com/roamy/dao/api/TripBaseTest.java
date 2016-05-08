package com.roamy.dao.api;

import com.roamy.domain.*;
import com.roamy.util.DomainObjectUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 4/23/2016.
 */
public abstract class TripBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripRepositoryTest.class);

    @Autowired
    protected TripRepository tripRepository;

    @Autowired
    protected TripInstanceRepository tripInstanceRepository;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected DateTime today = DateTime.now().withTime(0, 0, 0, 0);

    protected PackageTrip createPackageTrip(String code, int thrillMeter, Double pricePerAdult, Status status,
                                          List<Category> categories, List<City> targetCities) {
        PackageTrip trip = new PackageTrip();
        DomainObjectUtil.setPropertiesForTrip(trip, code, thrillMeter, pricePerAdult, status);
        trip.setNumberOfDays(5);
        trip = tripRepository.save(trip);

        trip.setCategories(categories);
        trip.setTargetCities(targetCities);
        trip = tripRepository.save(trip);
        return trip;
    }

    protected PackageTripInstance createPackageTripInstance(PackageTrip trip, Date date, Status status) {
        PackageTripInstance tripInstance = new PackageTripInstance();
        DomainObjectUtil.setPropertiesForTripInstance(tripInstance, trip.getCode(), trip.getThrillMeter(), trip.getPricePerAdult(), status);
        tripInstance.setNumberOfDays(5);
        tripInstance.setDate(date);
        tripInstance.setTrip(trip);

        List<TripInstanceOption> options = new ArrayList<>();
        options.add(DomainObjectUtil.createTripInstanceOption("option1", 1000.0));
        options.add(DomainObjectUtil.createTripInstanceOption("option2", 1500.0));
        tripInstance.setOptions(options);

        tripInstance = tripInstanceRepository.save(tripInstance);
        return tripInstance;
    }

    public void setUp() {

        // create cities
        City mumbai = cityRepository.save(DomainObjectUtil.createCity("MUMBAI", "Mumbai"));
        City pune = cityRepository.save(DomainObjectUtil.createCity("PUNE", "Pune"));

        Category thrill = categoryRepository.save(DomainObjectUtil.createCategory("THRILL", "thrill"));
        Category adventure = categoryRepository.save(DomainObjectUtil.createCategory("ADVENTURE", "adventure"));

        List<Category> categories1 = new ArrayList<>();
        categories1.add(thrill);
        List<City> cities1 = new ArrayList<>();
        cities1.add(mumbai);
        PackageTrip trip1 = createPackageTrip("TRIP1", 3, 4000.0, Status.Active, categories1, cities1);
        PackageTripInstance tripInstance1 = createPackageTripInstance(trip1, today.plusDays(1).toDate(), Status.Active);
        PackageTripInstance tripInstance11 = createPackageTripInstance(trip1, today.plusDays(2).toDate(), Status.Active);

        List<Category> categories2 = new ArrayList<>();
        categories2.add(adventure);
        List<City> cities2 = new ArrayList<>();
        cities2.add(pune);
        PackageTrip trip2 = createPackageTrip("TRIP2", 5, 1000.0, Status.Active, categories2, cities2);
        PackageTripInstance tripInstance2 = createPackageTripInstance(trip2, today.plusDays(3).toDate(), Status.Active);

        List<Category> categories3 = new ArrayList<>();
        categories3.add(thrill);
        categories3.add(adventure);
        List<City> cities3 = new ArrayList<>();
        cities3.add(mumbai);
        cities3.add(pune);
        PackageTrip trip3 = createPackageTrip("TRIP3", 1, 2000.0, Status.Active, categories3, cities3);
        PackageTripInstance tripInstance3 = createPackageTripInstance(trip3, today.plusDays(5).toDate(), Status.Active);

        PackageTrip trip4 = createPackageTrip("TRIP4", 4, 2000.0, Status.Inactive, categories3, cities3);
        PackageTripInstance tripInstance4 = createPackageTripInstance(trip4, today.plusDays(1).toDate(), Status.Inactive);
    }

    public void tearDown() {
//        tripInstanceRepository.deleteAll();
//        tripRepository.deleteAll();
//        cityRepository.deleteAll();
//        categoryRepository.deleteAll();
    }
}
