package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    public void testFindByStatusAndInstancesStatusAndInstancesDateBetween() throws Exception {
        tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetween(Status.Active, Status.Active, new Date(), new Date());
    }

    @Test
    public void testFindByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultAsc() throws Exception {
        tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultAsc(Status.Active, Status.Active, new Date(), new Date());
    }

    @Test
    public void testFindByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultDesc() throws Exception {
        tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultDesc(Status.Active, Status.Active, new Date(), new Date());
    }

    @Test
    public void testFindByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc() throws Exception {
        tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status.Active, Status.Active, new Date(), new Date());
    }

    @Test
    public void testFindByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc() throws Exception {
        tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status.Active, Status.Active, new Date(), new Date());
    }
}