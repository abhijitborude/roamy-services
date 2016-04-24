package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by Abhijit on 11/16/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class TripInstanceRepositoryTest {

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Test
    public void testFindByTripCodeAndStatus() throws Exception {
        tripInstanceRepository.findByTripCodeAndStatus("", Status.Active);
    }

    @Test
    public void testFindByTripCodeAndDateAndStatus() throws Exception {
        tripInstanceRepository.findByTripCodeAndDateAndStatus("", new Date(), Status.Active);
    }
}