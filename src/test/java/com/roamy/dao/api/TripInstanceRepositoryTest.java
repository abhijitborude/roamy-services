package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Status;
import com.roamy.domain.TripInstance;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Abhijit on 11/16/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class TripInstanceRepositoryTest extends TripBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripInstanceRepositoryTest.class);

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    private void assertTripInstanceResult(List<TripInstance> tripInstances, int expectedResults) {
        LOGGER.info("Number of tripInstances in the result: {}", tripInstances == null ? 0 : tripInstances.size());

        Assert.assertNotNull("There should be " + expectedResults + " tripInstances found", tripInstances);
        Assert.assertEquals("There should be " + expectedResults + " tripInstances found", expectedResults, tripInstances.size());
    }

    @Test
    public void testFindByTripCodeAndStatus() throws Exception {
        List<TripInstance> tripInstances = tripInstanceRepository.findTop60ByTripCodeAndStatus("TRIP1", Status.Active);
        assertTripInstanceResult(tripInstances, 2);

        tripInstances = tripInstanceRepository.findTop60ByTripCodeAndStatus("TRIP4", Status.Inactive);
        assertTripInstanceResult(tripInstances, 1);
    }

    @Test
    public void testFindByTripCodeAndDateAndStatus() throws Exception {
        DateTime today = DateTime.now().withTime(0, 0, 0, 0);

        List<TripInstance> tripInstances = tripInstanceRepository.
                findByTripCodeAndDateAndStatus("TRIP1", today.plusDays(2).toDate(), Status.Active);
        assertTripInstanceResult(tripInstances, 1);

        tripInstances = tripInstanceRepository.
                findByTripCodeAndDateAndStatus("TRIP4", today.plusDays(1).toDate(), Status.Inactive);
        assertTripInstanceResult(tripInstances, 1);
    }
}