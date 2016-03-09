package com.roamy.service.notification.impl;

import com.roamy.dao.api.SmsNotificationRepository;
import com.roamy.domain.SmsNotification;
import com.roamy.domain.Status;
import com.roamy.integration.sms.dto.SmsResult;
import com.roamy.integration.sms.service.api.SmsService;
import com.roamy.service.notification.api.SmsNotificationService;
import com.roamy.util.RoamyUtils;
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

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    @Override
    public void sendVerificationSms(String phoneNumber, String verificationCode) {
        LOGGER.info("Sending verification code {} to phoneNumber: {}", verificationCode, phoneNumber);

        String message = VERIFICATION_MSG + verificationCode;

        // create smsNotification object with pending status
        SmsNotification notification = new SmsNotification(phoneNumber, message, Status.Pending);
        RoamyUtils.addAuditPropertiesForCreateEntity(notification, "test");

        // send SMS
        SmsResult smsResult = smsService.sendSms(phoneNumber, message);

        if (smsResult.isSuccess()) {
            notification.setStatus(Status.Success);
        } else {
            notification.setStatus(Status.Failed);
            notification.setErrorCode(smsResult.getErrorCode());
            notification.setErrorDescription(smsResult.getErrorDescription());
        }

        smsNotificationRepository.save(notification);
    }
}
