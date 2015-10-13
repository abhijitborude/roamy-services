package com.roamy.service.impl;

import com.roamy.TestApplication;
import com.roamy.domain.City;
import com.roamy.dto.CityDto;
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

    private static final Logger log = LoggerFactory.getLogger(CityServiceImplTest.class);

    private static final Long CITY_ID_MUMBAI = 1L;
    private static final Long CITY_ID_PUNE = 2L;

    @Autowired
    CityService cityService;

    @Test
    public void testGetAllActiveCities() throws Exception {
        List<City> cities = cityService.getAllActiveCities();
        log.info("found active cities: {}", cities);

        assertNotNull("There should be at least one active city", cities);
        assertNotEquals("There should be at least one active city", 0, cities.size());
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
        CityDto dto = new CityDto();
        dto.setName("Test City");
        dto.setCreatedBy("test");

        City city = cityService.createCity(dto);
        assertNotNull("City could not be saved", city);
        assertNotNull("id after save can not be NULL", city.getId());
        assertEquals("name does not match", dto.getName(), city.getName());
        assertEquals("createdBy does not match", dto.getCreatedBy(), city.getCreatedBy());
    }

    @Test
    public void testCreateCityNoName() throws Exception {
        CityDto dto = new CityDto();
        dto.setCreatedBy("test");

        try {
            City city = cityService.createCity(dto);
            fail("City without name should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testCreateCityNoCreatedBy() throws Exception {
        CityDto dto = new CityDto();
        dto.setName("Test City");

        try {
            City city = cityService.createCity(dto);
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
        // first create a city
        CityDto dto = new CityDto();
        dto.setName("Test City");
        dto.setCreatedBy("test");

        City city = cityService.createCity(dto);
        assertNotNull("City could not be saved", city);

        // create a new dto with it's values
        dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName() + "-1");
        dto.setLastModifiedBy("test");

        City updateCity = cityService.updateCity(dto);
        assertNotNull("City could not be updated", updateCity);
        assertEquals("name was not updated", city.getName() + "-1", updateCity.getName());
        assertNotEquals("lastModifiedOn not updated", city.getLastModifiedOn(), updateCity.getLastModifiedOn());
        assertEquals("createdBy should not change after the update", city.getCreatedBy(), updateCity.getCreatedBy());
        assertEquals("createdOn should not change after the update", city.getCreatedOn(), updateCity.getCreatedOn());
        assertEquals("status should not change after the update", city.getStatus(), updateCity.getStatus());
    }

    @Test
    public void testUpdateCityNullName() throws Exception {
        // first create a city
        CityDto dto = new CityDto();
        dto.setName("Test City");
        dto.setCreatedBy("test");

        City city = cityService.createCity(dto);
        assertNotNull("City could not be saved", city);

        // create a new dto with it's values
        dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(null);
        dto.setLastModifiedBy("test");

        try {
            City updateCity = cityService.updateCity(dto);
            fail("city should not be updated with NULL name");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCityNullLastModifiedBy() throws Exception {
        // first create a city
        CityDto dto = new CityDto();
        dto.setName("Test City");
        dto.setCreatedBy("test");

        City city = cityService.createCity(dto);
        assertNotNull("City could not be saved", city);

        // create a new dto with it's values
        dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName() + "-1");

        try {
            City updateCity = cityService.updateCity(dto);
            fail("city should not be updated with NULL lastModifiedBy");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCityNullId() throws Exception {
        // first create a city
        CityDto dto = new CityDto();
        dto.setName("Test City");
        dto.setCreatedBy("test");

        City city = cityService.createCity(dto);
        assertNotNull("City could not be saved", city);

        // create a new dto with it's values
        dto = new CityDto();
        dto.setName(city.getName() + "-1");
        dto.setLastModifiedBy("test");

        try {
            City updateCity = cityService.updateCity(dto);
            fail("city should not be updated with NULL id");
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