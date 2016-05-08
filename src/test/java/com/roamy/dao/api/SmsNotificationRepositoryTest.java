package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.SmsNotification;
import com.roamy.domain.Status;
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

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 4/2/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = TestApplication.class)
public class SmsNotificationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsNotificationRepositoryTest.class);

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    private static final String TEST_PHONE_NUMBER1 = "12345";
    private static final String TEST_PHONE_NUMBER2 = "123456789";
    private static final String TEST_MESSAGE1 = "Test Message 1";
    private static final String TEST_MESSAGE2 = "Test Message 2";

    private SmsNotification createSmsNotification(String phoneNumber, String message, Status status) {
        SmsNotification notification = new SmsNotification();
        notification.setPhoneNumber(phoneNumber);
        notification.setMessage(message);
        notification.setStatus(status);
        notification.setCreatedBy("test");
        notification.setCreatedOn(new Date());
        notification.setLastModifiedBy("test");
        notification.setLastModifiedOn(new Date());
        return notification;
    }

    @Before
    public void setUp() {
        smsNotificationRepository.save(createSmsNotification(TEST_PHONE_NUMBER1, TEST_MESSAGE1, Status.Active));
        smsNotificationRepository.save(createSmsNotification(TEST_PHONE_NUMBER1, TEST_MESSAGE2, Status.Inactive));
        smsNotificationRepository.save(createSmsNotification(TEST_PHONE_NUMBER2, TEST_MESSAGE1, Status.Active));
    }

    @After
    public void tearDown() {
        //smsNotificationRepository.deleteAll();
    }

    @Test
    public void testFindByStatus() {
        List<SmsNotification> byStatus = smsNotificationRepository.findByStatus(Status.Active);
        Assert.assertEquals("There should be two sms notifications with Status Active", 2, byStatus.size());
    }

    @Test
    public void testFindByPhoneNumber() {
        List<SmsNotification> byPhoneNumber1 = smsNotificationRepository.findByPhoneNumber(TEST_PHONE_NUMBER1);
        Assert.assertEquals("There should be two sms notifications with phone number " + TEST_PHONE_NUMBER1, 2, byPhoneNumber1.size());

        List<SmsNotification> byPhoneNumber2 = smsNotificationRepository.findByPhoneNumber(TEST_PHONE_NUMBER2);
        Assert.assertEquals("There should be one sms notification with phone number " + TEST_PHONE_NUMBER2, 1, byPhoneNumber2.size());
        Assert.assertEquals("sms notification with phone number " + TEST_PHONE_NUMBER2 + " should have message as " + TEST_MESSAGE1,
                                TEST_MESSAGE1, byPhoneNumber2.get(0).getMessage());
    }

    @Test
    public void testFindByPhoneNumberAndStatus() {
        List<SmsNotification> byPhoneNumberAndStatus = smsNotificationRepository.findByPhoneNumberAndStatus(TEST_PHONE_NUMBER1, Status.Inactive);
        Assert.assertEquals("There should be one sms notification with phone number " + TEST_PHONE_NUMBER1, 1, byPhoneNumberAndStatus.size());
        Assert.assertEquals("sms notification with phone number " + TEST_PHONE_NUMBER1 + " should have message as " + TEST_MESSAGE2,
                TEST_MESSAGE2, byPhoneNumberAndStatus.get(0).getMessage());
    }
}