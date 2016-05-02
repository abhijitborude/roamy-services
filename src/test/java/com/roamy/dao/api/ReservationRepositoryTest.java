package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.*;
import com.roamy.util.DomainObjectUtil;
import com.roamy.util.RoamyUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 2/15/16.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class ReservationRepositoryTest extends TripBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepositoryTest.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTripOptionRepository reservationTripOptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        super.setUp();

        User user = DomainObjectUtil.createUser("12345", "a@a.com", "fname", "lname");
        user = userRepository.save(user);

        List<TripInstance> tripInstances = tripInstanceRepository.findByTripCodeAndDateAndStatus("TRIP1", today.plusDays(1).toDate(), Status.Active);
        PackageTripInstance tripInstance = (PackageTripInstance) tripInstances.get(0);

        Reservation reservation = new PackageReservation();
        DomainObjectUtil.addPropertiesToReservation(reservation, 1000.0, tripInstance.getDate(), "abc@abc.com", "12345", Status.Pending);
        reservation.setUser(user);
        reservation.setTripInstances(tripInstances);

        List<ReservationTripOption> reservationTripOptions = new ArrayList<>();
        tripInstance.getOptions().forEach(e -> {
            ReservationTripOption reservationTripOption = new ReservationTripOption();
            reservationTripOption.setTripInstanceOption(e);
            reservationTripOption.setCount(2);
            reservationTripOption.setStatus(Status.Active);
            RoamyUtils.addAuditPropertiesForCreateEntity(reservationTripOption, "test");

            reservationTripOptions.add(reservationTripOption);
        });
        reservation.setTripOptions(reservationTripOptions);

        reservation.addPayment(DomainObjectUtil.createReservationPayment(500.0, PaymentType.Romoney));
        reservation = reservationRepository.save(reservation);
        LOGGER.info("Saved {}", reservation);
    }

    @After
    public void tearDown() {
        reservationTripOptionRepository.deleteAll();
        reservationRepository.deleteAll();
        super.tearDown();
        userRepository.deleteAll();
    }

    @Test
    public void testFindOne() {
        Reservation reservation = reservationRepository.findOne(1L);
        LOGGER.info("found {}", reservation);
    }

//    @Test
//    public void testFindTop50UserIdOrderByStartDateDesc() throws Exception {
//        List<Reservation> reservations = reservationRepository.findTop50ByUserIdOrderByStartDateDesc(1L);
//        LOGGER.info("found {}", reservations);
//    }
//
//    @Test
//    public void testFindTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc() throws Exception {
//        List<Reservation> reservations = reservationRepository.findTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc(1L, new Date());
//        LOGGER.info("found {}", reservations);
//    }
//
//    @Test
//    public void testFindByUserIdAndStatusNotAndStartDateGreaterThanEqualOrderByStartDateAsc() throws Exception {
//        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusNotAndStartDateGreaterThanEqualOrderByStartDateAsc(1L, Status.Active, new Date());
//        LOGGER.info("found {}", reservations);
//    }
//
//    @Test
//    public void testFindByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc() throws Exception {
//        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(1L, Status.Active, new Date());
//        LOGGER.info("found {}", reservations);
//    }
}