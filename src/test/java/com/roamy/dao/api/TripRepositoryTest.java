package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.*;
import com.roamy.util.DomainObjectUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 4/9/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Long tripId;

    private void setPropertiesForTrip(Trip trip, String code, List<City> cities, List<Category> categories, int thrillMeter, Double pricePerAdult) {
        trip.setCode(code);
        trip.setName(code);
        trip.setDescription(code);
        trip.setCategories(categories);
        trip.setTargetCities(cities);
        trip.setThrillMeter(thrillMeter);
        trip.setPricePerAdult(pricePerAdult);
        trip.setStatus(Status.Active);
        trip.setCreatedBy("test");
        trip.setLastModifiedBy("test");
    }

    private PackageTrip createPackageTrip(String code, List<City> cities, List<Category> categories, int thrillMeter, Double pricePerAdult, int numberOfDays) {
        PackageTrip trip = new PackageTrip();
        setPropertiesForTrip(trip, code, cities, categories, thrillMeter, pricePerAdult);
        trip.setNumberOfDays(numberOfDays);
        return trip;
    }

    @Before
    public void setUp() {

        // create cities
        City mumbai = cityRepository.save(DomainObjectUtil.createCity("MUMBAI", "Mumbai"));
        City pune = cityRepository.save(DomainObjectUtil.createCity("PUNE", "Pune"));

        Category thrill = categoryRepository.save(DomainObjectUtil.createCategory("THRILL", "thrill"));
        Category adventure = categoryRepository.save(DomainObjectUtil.createCategory("ADVENTURE", "adventure"));

        Trip trip = new PackageTrip();

    }

    @After
    public void tearDown() {
        tripRepository.deleteAll();
    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc() {

    }

    @Test
    public void testFindByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc() {

    }
}