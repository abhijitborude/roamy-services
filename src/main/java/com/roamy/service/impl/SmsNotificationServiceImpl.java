package com.roamy.service.impl;

import com.roamy.integration.sms.service.api.SmsService;
import com.roamy.service.api.SmsNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Abhijit on 12/9/2015.
 */
@Service("smsNotificationService")
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsNotificationServiceImpl.class);

    private static final String VERIFICATION_MSG = "Your ROAMY verification code is ";

    @Autowired
    private SmsService smsService;

    @Override
    public void sendVerificationSms(String phoneNumber, String verificationCode) {
        LOGGER.info("Sending verification code {} to phoneNumber: {}", verificationCode, phoneNumber);

        smsService.sendSms(phoneNumber, VERIFICATION_MSG + verificationCode);
    }
}
