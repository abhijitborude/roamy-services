package com.roamy.service.discount.impl;

import com.roamy.TestApplication;
import com.roamy.service.discount.api.RomoneyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 5/1/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class RomoneyServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RomoneyServiceImplTest.class);

    @Autowired
    private RomoneyService romoneyService;

    @Test
    public void testGetRomoneyToApply() throws Exception {

    }
}