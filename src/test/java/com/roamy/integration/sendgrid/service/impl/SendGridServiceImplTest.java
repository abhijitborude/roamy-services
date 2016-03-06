package com.roamy.integration.sendgrid.service.impl;

import com.roamy.TestApplication;
import com.roamy.integration.sendgrid.dto.SendGridEmailDto;
import com.roamy.integration.sendgrid.service.api.SendGridService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 2/14/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class SendGridServiceImplTest {

    @Autowired
    private SendGridService sendGridService;

    @Test
    public void testSendEmail() throws Exception {
        SendGridEmailDto dto = new SendGridEmailDto();
        dto.addToAddress("abhijitab@gmail.com");
//        dto.addToAddress("raahulborude@gmail.com");
//        dto.addToAddress("osigroups@gmail.com");
        dto.setCategory("Test");
        dto.setFromName("Roamy");
        dto.setFromAddress("roamy@roamy.com");
        dto.setReplyToAddress("no-reply@roamy.com");
        dto.setSubject("Test");
        dto.setHtml("This is a test email. Please ignore.");

        sendGridService.sendEmail(dto);
    }
}