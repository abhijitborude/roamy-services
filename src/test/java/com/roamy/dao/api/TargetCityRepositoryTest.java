package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Status;
import com.roamy.domain.TargetCity;
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
 * Created by Abhijit on 11/21/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class TargetCityRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(TargetCityRepositoryTest.class);

    private static final String CITY_CODE_MUMBAI = "MUMBAI";

    @Autowired
    private TargetCityRepository targetCityRepository;

    @Test
    public void testFindByCityCodeAndTripStatus() throws Exception {
        List<TargetCity> targetCities = targetCityRepository.findByCityCodeAndTripStatus(CITY_CODE_MUMBAI, Status.Active);
        Assert.assertNotNull("TargetCities for " + CITY_CODE_MUMBAI + "should not be NULL", targetCities);
    }
}