package com.roamy.service.impl;

import com.roamy.TestApplication;
import com.roamy.domain.City;
import com.roamy.service.api.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CityServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(CityServiceImplTest.class);

    @Autowired
    CityService cityService;

    @Test
    public void testGetAllActiveCities() throws Exception {
        List<City> cities = cityService.getAllActiveCities();
        log.info("found active cities: {}", cities);

        Assert.assertNotNull("There should be at least one active city", cities);
        Assert.assertNotEquals("There should be at least one active city", 0, cities.size());
    }

}