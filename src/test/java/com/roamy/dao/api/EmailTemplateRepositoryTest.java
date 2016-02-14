package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.EmailTemplate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 2/6/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class EmailTemplateRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateRepositoryTest.class);
    public static final String TEST_TEMPLATE_CODE = "TEST_TEMPLATE_CODE";
    public static final String TEST_TEMPLATE_NAME = "Test Template";
    public static final String TEST_TEMPLATE_TYPE = "Test Type";

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Before
    public void setUp() throws Exception {

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setName(TEST_TEMPLATE_NAME);
        emailTemplate.setCode(TEST_TEMPLATE_CODE);
        emailTemplate.setDescription("Test Description");
        emailTemplate.setEmailType(TEST_TEMPLATE_TYPE);
        emailTemplate.setTemplate("<h1>test</h1>");
        emailTemplate.setCreatedBy("test");
        emailTemplate.setLastModifiedBy("test");

        emailTemplateRepository.save(emailTemplate);
    }

    @After
    public void tearDown() throws Exception {
        emailTemplateRepository.deleteAll();
    }

    @Test
    public void testFindByCode() throws Exception {
        EmailTemplate template = emailTemplateRepository.findByCode(TEST_TEMPLATE_CODE);
        LOGGER.info("found " + template);

        Assert.assertNotNull(TEST_TEMPLATE_CODE + " not found", template);
        Assert.assertEquals("Name of the EmailTemplate should be '" + TEST_TEMPLATE_NAME + "'", TEST_TEMPLATE_NAME, template.getName());
        Assert.assertEquals("Type of the EmailTemplate should be '" + TEST_TEMPLATE_TYPE + "'", TEST_TEMPLATE_TYPE, template.getEmailType());
    }
}