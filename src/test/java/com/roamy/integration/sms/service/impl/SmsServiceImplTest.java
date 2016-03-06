package com.roamy.integration.sms.service.impl;

import com.roamy.TestApplication;
import com.roamy.integration.sms.service.api.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 12/8/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class SmsServiceImplTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void testSendSms() throws Exception {
        smsService.sendSms("0", "Your ROAMY verification code is 123456");
    }
}