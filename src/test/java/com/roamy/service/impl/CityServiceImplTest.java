package com.roamy.service.impl;

import com.roamy.TestApplication;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.service.api.CityService;
import com.roamy.util.RoamyValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 10/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CityServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImplTest.class);

    private static final Long CITY_ID_MUMBAI = 1L;
    private static final Long CITY_ID_PUNE = 2L;

    @Autowired
    CityService cityService;

    @Test
    public void testGetAllCities() throws Exception {
        List<City> cities = cityService.getAllCities();
        logger.info("found all cities: {}", cities);

        assertNotNull("There should be at least one city", cities);
        assertNotEquals("There should be at least one city", 0, cities.size());

    }

    @Test
    public void testGetAllActiveCities() throws Exception {
        List<City> cities = cityService.getAllActiveCities();
        logger.info("found active cities: {}", cities);

        assertNotNull("There should be at least one active city", cities);
        assertNotEquals("There should be at least one active city", 0, cities.size());

        for (City city : cities) {
            if (!Status.Active.equals(city.getStatus())) {
                fail("Inactive city found");
            }
        }
    }

    @Test
    public void testGetCityById() throws Exception {
        City city = cityService.getCityById(CITY_ID_MUMBAI);

        assertNotNull("There should be a city with id: " + CITY_ID_MUMBAI, city);
        assertEquals("City name should be Mumbai", "Mumbai", city.getName());
    }

    @Test
    public void testGetCitiesByName() throws Exception {
        String cityName = "mumbai";
        List<City> cities = cityService.getCitiesByName(cityName);

        assertNotNull("There should be at least one city with name: " + cityName, cities);
        for (City city : cities) {
            assertTrue("City with name different from " + cityName + " found", city.getName().toLowerCase().equals(cityName.toLowerCase()));
        }
    }

    @Test
    public void testCreateCity() throws Exception {
        City city = new City();
        city.setName("Test City");
        city.setCreatedBy("test");

        City newCity = cityService.createCity(city);
        assertNotNull("City could not be saved", newCity);
        assertNotNull("id after save can not be NULL", newCity.getId());
        assertEquals("name does not match", city.getName(), newCity.getName());
        assertEquals("createdBy does not match", city.getCreatedBy(), newCity.getCreatedBy());
    }

    @Test
    public void testCreateCityNoName() throws Exception {
        City city = new City();
        city.setCreatedBy("test");

        try {
            City newCity = cityService.createCity(city);
            fail("City without name should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testCreateCityNoCreatedBy() throws Exception {
        City city = new City();
        city.setName("Test City");

        try {
            City newCity = cityService.createCity(city);
            fail("City without createdBy should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testCreateCityNull() throws Exception {
        try {
            City city = cityService.createCity(null);
            fail("Null city should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCity() throws Exception {
        // first create a newCity
        City city = new City();
        city.setName("Test City");
        city.setCreatedBy("test");

        City newCity = cityService.createCity(city);
        assertNotNull("City could not be saved", newCity);

        // create a new city with it's values
        city = new City();
        city.setId(newCity.getId());
        city.setName(newCity.getName() + "-1");
        city.setLastModifiedBy("test");

        City updatedCity = cityService.updateCity(city);
        assertNotNull("City could not be updated", updatedCity);
        assertEquals("name was not updated", newCity.getName() + "-1", updatedCity.getName());
        assertNotEquals("lastModifiedOn not updated", newCity.getLastModifiedOn(), updatedCity.getLastModifiedOn());
        assertEquals("createdBy should not change after the update", newCity.getCreatedBy(), updatedCity.getCreatedBy());
        assertEquals("createdOn should not change after the update", newCity.getCreatedOn(), updatedCity.getCreatedOn());
        assertEquals("status should not change after the update", newCity.getStatus(), updatedCity.getStatus());
    }

    @Test
    public void testUpdateCityNullName() throws Exception {
        // first create a newCity
        City city = new City();
        city.setName("Test City");
        city.setCreatedBy("test");

        City newCity = cityService.createCity(city);
        assertNotNull("City could not be saved", newCity);

        // create a new city with it's values
        city = new City();
        city.setId(newCity.getId());
        city.setName(null);
        city.setLastModifiedBy("test");

        try {
            City updateCity = cityService.updateCity(city);
            fail("newCity should not be updated with NULL name");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCityNullLastModifiedBy() throws Exception {
        // first create a newCity
        City city = new City();
        city.setName("Test City");
        city.setCreatedBy("test");

        City newCity = cityService.createCity(city);
        assertNotNull("City could not be saved", newCity);

        // create a new city with it's values
        city = new City();
        city.setId(newCity.getId());
        city.setName(newCity.getName() + "-1");

        try {
            City updateCity = cityService.updateCity(city);
            fail("newCity should not be updated with NULL lastModifiedBy");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCityNullId() throws Exception {
        // first create a newCity
        City city = new City();
        city.setName("Test City");
        city.setCreatedBy("test");

        City newCity = cityService.createCity(city);
        assertNotNull("City could not be saved", newCity);

        // create a new city with it's values
        city = new City();
        city.setName(newCity.getName() + "-1");
        city.setLastModifiedBy("test");

        try {
            City updateCity = cityService.updateCity(city);
            fail("newCity should not be updated with NULL id");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCityNull() throws Exception {
        try {
            City updateCity = cityService.updateCity(null);
            fail("NULL city should not be updated");
        } catch (RoamyValidationException e) {

        }
    }
}