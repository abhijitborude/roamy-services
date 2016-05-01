package com.roamy.service.notification.impl;

import com.roamy.config.ConfigProperties;
import com.roamy.dao.api.EmailNotificationRepository;
import com.roamy.dao.api.EmailTemplateRepository;
import com.roamy.domain.*;
import com.roamy.integration.paymentGateway.dto.PaymentDto;
import com.roamy.integration.sendgrid.dto.SendGridEmailDto;
import com.roamy.integration.sendgrid.service.api.SendGridService;
import com.roamy.service.notification.api.EmailNotificationService;
import com.roamy.service.notification.dto.TripNotificationDto;
import com.roamy.service.notification.dto.TripOptionNotificationDto;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyUtils;
import com.roamy.util.TemplateTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abhijit on 2/2/2016.
 */
@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Autowired
    private SendGridService sendGridService;

    @Autowired
    private TemplateTranslator templateTranslator;

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void sendTripReservationEmail(Reservation reservation) {
        // prepare params
        TripInstance tripInstance = reservation.getTripInstances().get(0);

        TripNotificationDto dto = new TripNotificationDto();
        dto.setReservationId(reservation.getId());
        dto.setName(tripInstance.getName());
        dto.setDate(RoamyUtils.getEmailFormattedDate(reservation.getStartDate()));
        dto.setCoverPicture(tripInstance.getTrip().getCoverPicture() != null ?
                            tripInstance.getTrip().getCoverPicture() :
                            "http://res.cloudinary.com/abhijitab/image/upload/v1462058721/default_email_cover_ox4pz0.jpg");
        dto.setUser(reservation.getUser().getFirstName());
        dto.setTotalCost(RoamyUtils.getEmailFormattedCurrency(reservation.getAmount()));

        List<TripOptionNotificationDto> options = new ArrayList<>();
        reservation.getTripOptions().stream().forEach(reservationOption -> {
            TripOptionNotificationDto option = new TripOptionNotificationDto();
            option.setType(reservationOption.getTripInstanceOption().getName());
            option.setPrice(RoamyUtils.getEmailFormattedCurrency(reservationOption.getTripInstanceOption().getPrice()));
            option.setCount(String.valueOf(reservationOption.getCount()));
            double totalCost = reservationOption.getTripInstanceOption().getPrice() * reservationOption.getCount();
            option.setTotalCost(RoamyUtils.getEmailFormattedCurrency(totalCost));

            options.add(option);
        });

        reservation.getPayments().stream()
            .filter(payment -> PaymentType.Romoney.equals(payment.getType()) || PaymentType.Discount.equals(payment.getType()))
            .forEach(payment -> {
                TripOptionNotificationDto option = new TripOptionNotificationDto();
                option.setType(payment.getType().toString());
                option.setPrice("");
                option.setCount("");
                option.setTotalCost("-" + RoamyUtils.getEmailFormattedCurrency(payment.getAmount()));

                options.add(option);
        });

        dto.setOptions(options);

        if (tripInstance instanceof PackageTripInstance) {
            dto.setMeetingPoints(((PackageTripInstance) tripInstance).getMeetingPoints());
            dto.setThingsToCarry(((PackageTripInstance) tripInstance).getThingsToCarry());
        } else {
            dto.setMeetingPoints("");
            dto.setThingsToCarry("");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("trip", dto);

        // create json to store params
        String paramsJson = RestUtils.convertToJson(dto);

        // load template
        EmailTemplate emailTemplate = emailTemplateRepository.findByCode("RESERVATION_CONFIRMATION");

        // create email
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setEmail(reservation.getEmail());
        emailNotification.setEmailTemplate(emailTemplate);
        emailNotification.setReservationId(reservation.getId());
        emailNotification.setUserId(reservation.getUser().getId());
        emailNotification.setParams(paramsJson);
        emailNotification.setStatus(Status.Pending);

        // later move email send logic to an async process
        String subject = templateTranslator.translate(emailTemplate.getSubjectTemplate(), params);
        String content = templateTranslator.translate(emailTemplate.getTemplate(), params);

        try {
            sendEmail(emailNotification, subject, content);
            emailNotification.setStatus(Status.Success);

        } catch (RuntimeException e) {
            LOGGER.error("Error while sending email: " + emailNotification);
            emailNotification.setStatus(Status.Failed);
        }

        emailNotificationRepository.save(emailNotification);
    }

    private void sendEmail(EmailNotification email, String subject, String content) {
        SendGridEmailDto dto = new SendGridEmailDto();
        dto.addToAddress(email.getEmail());
        dto.setCategory("Booking");

        dto.setFromName("Roamy");
        dto.setFromAddress("roamy@roamy.com");
        dto.setReplyToAddress("no-reply@roamy.com");

        dto.setSubject(subject);
        dto.setHtml(content);

        sendGridService.sendEmail(dto);
    }
}
