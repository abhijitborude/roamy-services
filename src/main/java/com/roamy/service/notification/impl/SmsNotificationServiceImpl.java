package com.roamy.service.notification.impl;

import com.roamy.dao.api.SmsNotificationRepository;
import com.roamy.domain.*;
import com.roamy.integration.sms.dto.SmsResult;
import com.roamy.integration.sms.service.api.SmsService;
import com.roamy.service.notification.api.SmsNotificationService;
import com.roamy.service.notification.dto.TripNotificationDto;
import com.roamy.util.RoamyUtils;
import com.roamy.util.TemplateTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhijit on 12/9/2015.
 */
@Service("smsNotificationService")
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsNotificationServiceImpl.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private TemplateTranslator templateTranslator;

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    @Override
    public SmsNotification sendVerificationSms(String phoneNumber, String verificationCode) {
        LOGGER.info("Sending verification code {} to phoneNumber: {}", verificationCode, phoneNumber);

        String message = "Your ROAMY verification code is " + verificationCode;
        SmsNotification notification = sendSmsNotification(phoneNumber, message);

        LOGGER.info("Verification code sent to phoneNumber: {}", phoneNumber);
        return notification;
    }

    @Override
    public SmsNotification sendTripReservationShareSms(Reservation reservation, String userName, String phoneNumber) {

        LOGGER.info("Sending trip details of reservation ({}) to {} at {}", reservation.getId(), userName, phoneNumber);

        TripNotificationDto tripDto = new TripNotificationDto();
        TripInstance tripInstance = reservation.getTripInstances().get(0);
        tripDto.setName(tripInstance.getName());
        tripDto.setDate(RoamyUtils.getEmailFormattedDate(reservation.getStartDate()));

        Map<String, Object> params = new HashMap<>();
        params.put("trip", tripDto);
        params.put("user", userName);
        params.put("sender", reservation.getUser().getFirstName());
        params.put("roamyLink", "http://goog.le/roamy");

        StringBuffer template = new StringBuffer("Hi $user, you have been invited by $sender");
        if (tripInstance instanceof TicketTripInstance) {
            template.append(" to have fun at \"$trip.name\" on $trip.date.");
        } else if (tripInstance instanceof StayTripInstance) {
            template.append(" for a 4 day experience at  \"$trip.name\" starting $trip.date.");
        }  else {
            // this should be PackageTripInstance
            template.append(" to participate in \"$trip.name\" on $trip.date.");
        }
        template.append(" Start ROAMYing! Download ROAMY at $roamyLink");

        String message = templateTranslator.translate(template.toString(), params);
        SmsNotification notification = sendSmsNotification(phoneNumber, message);

        LOGGER.info("Trip details sent to {} at {}", userName, phoneNumber);
        return notification;
    }

    private SmsNotification sendSmsNotification(String phoneNumber, String message) {
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

        return smsNotificationRepository.save(notification);
    }
}
