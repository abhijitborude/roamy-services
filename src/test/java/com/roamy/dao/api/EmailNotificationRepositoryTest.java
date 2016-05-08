package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.EmailNotification;
import com.roamy.domain.EmailTemplate;
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

/**
 * Created by Abhijit on 4/3/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = TestApplication.class)
public class EmailNotificationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationRepositoryTest.class);

    public static final String TEST_TEMPLATE_CODE = "TEST_TEMPLATE_CODE";
    public static final String TEST_TEMPLATE_NAME = "Test Template";
    public static final String TEST_TEMPLATE_TYPE = "Test Type";
    public static final String EMAIL = "abc@xyz.com";

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    private Long emailNotificationId;

    @Before
    public void setUp() {
        // create a template first
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setName(TEST_TEMPLATE_NAME);
        emailTemplate.setCode(TEST_TEMPLATE_CODE);
        emailTemplate.setDescription("Test Description");
        emailTemplate.setEmailType(TEST_TEMPLATE_TYPE);
        emailTemplate.setSubjectTemplate("test subject");
        emailTemplate.setTemplate("<h1>test</h1>");
        emailTemplate.setCreatedBy("test");
        emailTemplate.setLastModifiedBy("test");

        emailTemplate = emailTemplateRepository.save(emailTemplate);

        // then create the email notification
        EmailNotification notification = new EmailNotification();
        notification.setEmail(EMAIL);
        notification.setEmailTemplate(emailTemplate);
        notification.setSubjectParams("{\"key\": \"value\"}");
        notification.setParams("{\"key\": \"value\"}");
        notification.setUserId(1L);
        notification.setReservationId(1L);
        notification.setStatus(Status.Active);
        notification.setCreatedBy("test");
        notification.setLastModifiedBy("test");

        EmailNotification emailNotification = emailNotificationRepository.save(notification);

        emailNotificationId = emailNotification.getId();
    }

    @After
    public void tearDown() {
//        emailNotificationRepository.deleteAll();
//        emailTemplateRepository.deleteAll();
    }

    @Test
    public void testFindEmailNotification() {
        EmailNotification emailNotification = emailNotificationRepository.findOne(emailNotificationId);
        LOGGER.info("Found {}", emailNotification);

        Assert.assertEquals("Email template code should be " + TEST_TEMPLATE_CODE, TEST_TEMPLATE_CODE,
                                emailNotification.getEmailTemplate().getCode());
        Assert.assertEquals("Email should be " + EMAIL, EMAIL, emailNotification.getEmail());
    }
}