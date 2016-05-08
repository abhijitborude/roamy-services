package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.util.DomainObjectUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = TestApplication.class)
public class CityRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityRepositoryTest.class);

    private static final String TEST_NAME = "Test City";
    private static final String TEST_CODE = "TEST_CITY";

    private Long id;

    @Autowired
    CityRepository cityRepository;

    @Before
    public void setUp() {
        City city = cityRepository.save(DomainObjectUtil.createCity(TEST_CODE, TEST_NAME));
        id = city.getId();
        LOGGER.info("City saved with id: " + id);
    }

    @After
    public void tearDown() {
        //cityRepository.deleteAll();
    }

    @Test
    public void findCityByIdTest() {
        City city = cityRepository.findOne(id);
        LOGGER.info("findOne by id {}: {}", id, city);

        Assert.assertNotNull("There should be a city with id: " + id, city);
        Assert.assertEquals("City name should be " + TEST_NAME, TEST_NAME, city.getName());
    }

    @Test
    public void findAllCityTest() {
        Iterable<City> cities = cityRepository.findAll();
        LOGGER.info("findAll: {}", cities);

        Assert.assertNotNull("There should be at least one city", cities);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        String cityName = TEST_NAME.toLowerCase();
        List<City> categories = cityRepository.findByNameIgnoreCase(cityName);
        LOGGER.info("findByNameIgnoreCase by {}: {}", cityName, categories);

        Assert.assertNotNull("There should be at least one city with name: " + cityName, categories);
        for (City city : categories) {
            Assert.assertTrue("City with name different from " + cityName + " found", city.getName().toLowerCase().equals(cityName.toLowerCase()));
        }
    }

    @Test
    public void testFindByStatus() throws Exception {
        List<City> categories = cityRepository.findByStatus(Status.Active);
        LOGGER.info("findByStatus Inactive: {}", categories);

        Assert.assertNotNull("There should be at least one active city", categories);
    }
}
