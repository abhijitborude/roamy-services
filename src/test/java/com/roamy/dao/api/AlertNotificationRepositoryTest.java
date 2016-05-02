package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.*;
import com.roamy.util.DomainObjectUtil;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 4/3/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class AlertNotificationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertNotificationRepositoryTest.class);
    public static final String MESSAGE1 = "You have received 50 Romoney";
    public static final String MESSAGE2 = "You have received 100 Romoney";
    public static final String MESSAGE3 = "Book today and get 25% off";
    public static final String MESSAGE4 = "Book today and get 50% off";

    @Autowired
    private AlertNotificationRepository alertNotificationRepository;

    @Autowired
    private UserRepository userRepository;

    private Long userId;

    private Alert populateAlert(Alert alert, User user, String title, String message, Date expiryDate) {
        alert.setUser(user);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setExpiryDate(expiryDate);
        alert.setReadStatus(false);
        alert.setStatus(Status.Active);
        alert.setCreatedBy("test");
        alert.setLastModifiedBy("test");
        return alert;
    }

    @Before
    public void setUp() {
        User user = DomainObjectUtil.createUser("12345", "abc@nyx.com", "fname", "lname");
        user = userRepository.save(user);
        userId = user.getId();

        Alert alert = populateAlert(new InfoAlert(), user, MESSAGE1, MESSAGE1, (new DateTime()).plusDays(2).toDate());
        alertNotificationRepository.save(alert);

        alert = populateAlert(new InfoAlert(), user, MESSAGE2, MESSAGE2, (new DateTime()).minusDays(2).toDate());
        alertNotificationRepository.save(alert);

        alert = populateAlert(new PromoAlert(), user, MESSAGE3, MESSAGE3, (new DateTime()).plusDays(1).toDate());
        alertNotificationRepository.save(alert);

        alert = populateAlert(new PromoAlert(), user, MESSAGE4, MESSAGE4, (new DateTime()).minusDays(1).toDate());
        alertNotificationRepository.save(alert);
    }

    @After
    public void tearDown() {
        alertNotificationRepository.deleteAll();
    }

    @Test
    public void testFindByUserIdAndStatusAndExpiryDateGreaterThan() {
        List<Alert> alerts = alertNotificationRepository.findByUserIdAndStatusAndExpiryDateGreaterThan(userId, Status.Active, new Date());
        LOGGER.info("Found alerts: {}", alerts);
        Assert.assertEquals("There should be two alerts found", 2, alerts.size());
        Assert.assertTrue("Incorrect alerts loaded", MESSAGE1.equals(alerts.get(0).getTitle()) || MESSAGE3.equals(alerts.get(0).getTitle()));
        Assert.assertTrue("Incorrect alerts loaded", MESSAGE1.equals(alerts.get(1).getTitle()) || MESSAGE3.equals(alerts.get(1).getTitle()));

        alerts = alertNotificationRepository.findByUserIdAndStatusAndExpiryDateGreaterThan(userId, Status.Active, (new DateTime()).plusDays(1).toDate());
        LOGGER.info("Found alerts: {}", alerts);
        Assert.assertEquals("There should be one alert found", 1, alerts.size());

        alerts = alertNotificationRepository.findByUserIdAndStatusAndExpiryDateGreaterThan(userId, Status.Active, (new DateTime()).minusDays(5).toDate());
        LOGGER.info("Found alerts: {}", alerts);
        Assert.assertEquals("There should be two alerts found", 4, alerts.size());
    }
}