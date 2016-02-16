package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Reservation;
import com.roamy.domain.Status;
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
 * Created by Abhijit on 2/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class ReservationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepositoryTest.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testFindTop50UserIdOrderByStartDateDesc() throws Exception {
        List<Reservation> reservations = reservationRepository.findTop50ByUserIdOrderByStartDateDesc(1L);
        LOGGER.info("found {}", reservations);
    }

    @Test
    public void testFindTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc() throws Exception {
        List<Reservation> reservations = reservationRepository.findTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc(1L, new Date());
        LOGGER.info("found {}", reservations);
    }

    @Test
    public void testFindByUserIdAndStatusNotAndStartDateGreaterThanEqualOrderByStartDateAsc() throws Exception {
        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusNotAndStartDateGreaterThanEqualOrderByStartDateAsc(1L, Status.Active, new Date());
        LOGGER.info("found {}", reservations);
    }

    @Test
    public void testFindByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc() throws Exception {
        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(1L, Status.Active, new Date());
        LOGGER.info("found {}", reservations);
    }
}