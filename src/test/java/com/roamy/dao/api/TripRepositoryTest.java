package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.*;
import com.roamy.util.DomainObjectUtil;
import com.roamy.util.RoamyUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 4/9/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class TripRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripRepositoryTest.class);

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private void setPropertiesForTrip(Trip trip, String code, int thrillMeter, Double pricePerAdult, Status status) {
        trip.setCode(code);
        trip.setName(code);
        trip.setDescription(code);
        trip.setThrillMeter(thrillMeter);
        trip.setPricePerAdult(pricePerAdult);
        trip.setStatus(status);
        trip.setCreatedBy("test");
        trip.setLastModifiedBy("test");
    }

    private void setPropertiesForTripInstance(TripInstance tripInstance, String name, int thrillMeter, Double pricePerAdult, Status status) {
        tripInstance.setName(name);
        tripInstance.setDescription(name);
        tripInstance.setThrillMeter(thrillMeter);
        tripInstance.setPricePerAdult(pricePerAdult);
        tripInstance.setStatus(status);
        tripInstance.setCreatedBy("test");
        tripInstance.setLastModifiedBy("test");
    }

    private PackageTrip createPackageTripAndInstance(String code, int thrillMeter, Double pricePerAdult, Status status,
                                                     List<Category> categories, List<City> targetCities) {
        PackageTrip trip = new PackageTrip();
        setPropertiesForTrip(trip, code, thrillMeter, pricePerAdult, status);
        trip.setNumberOfDays(5);
        trip = tripRepository.save(trip);

        trip.setCategories(categories);
        trip.setTargetCities(targetCities);
        trip = tripRepository.save(trip);
        return trip;
    }

    private PackageTripInstance createPackageTripInstance(PackageTrip trip, Date date, Status status) {
        PackageTripInstance tripInstance = new PackageTripInstance();
        setPropertiesForTripInstance(tripInstance, trip.getCode(), trip.getThrillMeter(), trip.getPricePerAdult(), status);
        tripInstance.setNumberOfDays(5);
        tripInstance.setDate(date);
        tripInstance.setTrip(trip);
        tripInstance = tripInstanceRepository.save(tripInstance);
        return tripInstance;
    }

    @Before
    public void setUp() {

        // create cities
        City mumbai = cityRepository.save(DomainObjectUtil.createCity("MUMBAI", "Mumbai"));
        City pune = cityRepository.save(DomainObjectUtil.createCity("PUNE", "Pune"));

        Category thrill = categoryRepository.save(DomainObjectUtil.createCategory("THRILL", "thrill"));
        Category adventure = categoryRepository.save(DomainObjectUtil.createCategory("ADVENTURE", "adventure"));

        PackageTrip trip1 = createPackageTripAndInstance("TRIP1", 3, 4000.0, Status.Active,
                                    Arrays.asList(new Category[] {thrill}), Arrays.asList(new City[] {mumbai})
        );
        PackageTripInstance tripInstance1 = createPackageTripInstance(trip1, RoamyUtils.plusDays(new Date(), 1), Status.Active);
        PackageTripInstance tripInstance11 = createPackageTripInstance(trip1, RoamyUtils.plusDays(new Date(), 2), Status.Active);

        PackageTrip trip2 = createPackageTripAndInstance("TRIP2", 5, 1000.0, Status.Active,
                                    Arrays.asList(new Category[] {adventure}), Arrays.asList(new City[] {pune})
        );
        PackageTripInstance tripInstance2 = createPackageTripInstance(trip2, RoamyUtils.plusDays(new Date(), 3), Status.Active);

        PackageTrip trip3 = createPackageTripAndInstance("TRIP3", 1, 2000.0, Status.Active,
                                Arrays.asList(new Category[] {thrill, adventure}), Arrays.asList(new City[] {mumbai, pune})
        );
        PackageTripInstance tripInstance3 = createPackageTripInstance(trip3, RoamyUtils.plusDays(new Date(), 5), Status.Active);

        PackageTrip trip4 = createPackageTripAndInstance("TRIP4", 4, 2000.0, Status.Inactive,
                                Arrays.asList(new Category[] {thrill, adventure}), Arrays.asList(new City[] {mumbai, pune}));
        PackageTripInstance tripInstance4 = createPackageTripInstance(trip4, RoamyUtils.plusDays(new Date(), 1), Status.Inactive);
    }

    @After
    public void tearDown() {
        tripRepository.deleteAll();
        cityRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    private void assertTripResult(List<Trip> trips, int expectedResults) {
        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Assert.assertNotNull("There should be " + expectedResults + " trips found", trips);
        Assert.assertEquals("There should be " + expectedResults + " trips found", expectedResults, trips.size());
    }

    @Test
    public void testDistinctFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween() {

        List<Trip> trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                                                Arrays.asList(new String[]{"MUMBAI"}),
                                                Status.Active,
                                                new Date(),
                                                RoamyUtils.plusDays(new Date(), 1));
        assertTripResult(trips, 1);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"MUMBAI"}),
                Status.Active,
                new Date(),
                RoamyUtils.plusDays(new Date(), 3));
        assertTripResult(trips, 1);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"PUNE"}),
                Status.Active,
                RoamyUtils.plusDays(new Date(), 2),
                RoamyUtils.plusDays(new Date(), 5));
        assertTripResult(trips, 2);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"NO-CITY"}),
                Status.Active,
                new Date(),
                RoamyUtils.plusDays(new Date(), 4));
        assertTripResult(trips, 0);
    }

    @Test
    public void testDistinctFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(
                    Status.Active,
                    Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                    Status.Active,
                    new Date(),
                    RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with pricePerAdult {} previous trip", trip, trip.getPricePerAdult());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by pricePerAdult", previous.getPricePerAdult() <= trip.getPricePerAdult());
            }
            previous = trip;
        }
    }


    @Test
    public void testDistinctFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with pricePerAdult {} previous trip", trip, trip.getPricePerAdult());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by pricePerAdult", previous.getPricePerAdult() >= trip.getPricePerAdult());
            }
            previous = trip;
        }
    }


    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with thrillMeter {} previous trip", trip, trip.getThrillMeter());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by thrillMeter", previous.getThrillMeter() <= trip.getThrillMeter());
            }
            previous = trip;
        }
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with thrillMeter {} previous trip", trip, trip.getThrillMeter());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by thrillMeter", previous.getThrillMeter() >= trip.getThrillMeter());
            }
            previous = trip;
        }
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween() {
        List<Trip> trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"MUMBAI"}),
                Arrays.asList(new String[]{"THRILL"}),
                Status.Active,
                new Date(),
                RoamyUtils.plusDays(new Date(), 1));
        assertTripResult(trips, 1);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"MUMBAI"}),
                Arrays.asList(new String[]{"THRILL"}),
                Status.Active,
                new Date(),
                RoamyUtils.plusDays(new Date(), 3));
        assertTripResult(trips, 1);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"PUNE"}),
                Arrays.asList(new String[]{"ADVENTURE"}),
                Status.Active,
                RoamyUtils.plusDays(new Date(), 2),
                RoamyUtils.plusDays(new Date(), 5));
        assertTripResult(trips, 2);

        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                Arrays.asList(new String[]{"NO-CITY"}),
                Arrays.asList(new String[]{"NO-CATEGORY"}),
                Status.Active,
                new Date(),
                RoamyUtils.plusDays(new Date(), 4));
        assertTripResult(trips, 0);
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Arrays.asList(new String[]{"THRILL", "ADVENTURE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with pricePerAdult {} previous trip", trip, trip.getPricePerAdult());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by pricePerAdult", previous.getPricePerAdult() <= trip.getPricePerAdult());
            }
            previous = trip;
        }
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Arrays.asList(new String[]{"THRILL", "ADVENTURE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with pricePerAdult {} previous trip", trip, trip.getPricePerAdult());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by pricePerAdult", previous.getPricePerAdult() >= trip.getPricePerAdult());
            }
            previous = trip;
        }
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Arrays.asList(new String[]{"THRILL", "ADVENTURE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with thrillMeter {} previous trip", trip, trip.getThrillMeter());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by thrillMeter", previous.getThrillMeter() <= trip.getThrillMeter());
            }
            previous = trip;
        }
    }

    @Test
    public void testFindDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc() {
        List<Trip> trips = tripRepository.
                findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(
                        Status.Active,
                        Arrays.asList(new String[]{"MUMBAI", "PUNE"}),
                        Arrays.asList(new String[]{"THRILL", "ADVENTURE"}),
                        Status.Active,
                        new Date(),
                        RoamyUtils.plusDays(new Date(), 60));

        LOGGER.info("Number of trips in the result: {}", trips == null ? 0 : trips.size());

        Trip previous = null;
        for (Trip trip : trips) {
            LOGGER.info("Comparing trip {} with thrillMeter {} previous trip", trip, trip.getThrillMeter());
            if (previous != null) {
                Assert.assertTrue("Trips are not sorted by thrillMeter", previous.getThrillMeter() >= trip.getThrillMeter());
            }
            previous = trip;
        }
    }
}