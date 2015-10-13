package com.roamy.dao.impl;

import com.roamy.TestApplication;
import com.roamy.dao.api.CityRepository;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CityRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CityRepositoryTest.class);

    private static final Long CITY_ID_MUMBAI = 1L;
    private static final Long CITY_ID_PUNE = 2L;

    @Autowired
    CityRepository cityRepository;

    @Test
    public void findCityByIdTest() {
        City city = cityRepository.findOne(CITY_ID_MUMBAI);
        log.info("findOne by id {}: {}", CITY_ID_MUMBAI, city);

        Assert.assertNotNull("There should be a city with id: " + CITY_ID_MUMBAI, city);
        Assert.assertEquals("City name should be Mumbai", "Mumbai", city.getName());
    }

    @Test
    public void findAllCityTest() {
        Iterable<City> cities = cityRepository.findAll();
        log.info("findAll: {}", cities);

        Assert.assertNotNull("There should be at least one city", cities);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        String cityName = "mumbai";
        List<City> cities = cityRepository.findByNameIgnoreCase(cityName);
        log.info("findByNameIgnoreCase by {}: {}", cityName, cities);

        Assert.assertNotNull("There should be at least one city with name: " + cityName, cities);
        for (City city : cities) {
            Assert.assertTrue("City with name different from " + cityName + " found", city.getName().toLowerCase().equals(cityName.toLowerCase()));
        }
    }

    @Test
    public void testFindByNameIgnoreCaseLike() throws Exception {
        String cityName = "mum";
        List<City> cities = cityRepository.findByNameIgnoreCaseLike("%" + cityName + "%");
        log.info("findByNameIgnoreCaseLike '%{}%': {}", cityName, cities);

        Assert.assertNotNull("There should be at least one city with name like " + cityName, cities);
        for (City city : cities) {
            Assert.assertTrue("City with name not like " + cityName + " found", city.getName().toLowerCase().contains(cityName.toLowerCase()));
        }
    }

    @Test
    public void testFindByStatus() throws Exception {
        List<City> cities = cityRepository.findByStatus(Status.Inactive);
        log.info("findByStatus Inactive: {}", cities);

        Assert.assertNotNull("There should be at least one Inactive city", cities);
    }
}
