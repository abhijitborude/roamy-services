package com.roamy.service.notification.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roamy.dao.api.EmailNotificationRepository;
import com.roamy.dao.api.EmailTemplateRepository;
import com.roamy.domain.*;
import com.roamy.integration.sendgrid.dto.SendGridEmailDto;
import com.roamy.integration.sendgrid.service.api.SendGridService;
import com.roamy.service.notification.api.EmailNotificationService;
import com.roamy.service.notification.dto.TripNotificationDto;
import com.roamy.util.RoamyUtils;
import com.roamy.util.TemplateTranslator;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
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

    @Override
    public void sendTripReservationEmail(Reservation reservation) {
        // prepare params
        TripInstance tripInstance = reservation.getTripInstances().get(0);

        TripNotificationDto dto = new TripNotificationDto();
        dto.setReservationId(reservation.getId());
        dto.setName(tripInstance.getName());
        dto.setDate(RoamyUtils.getEmailFormattedDate(reservation.getStartDate()));

        Map<String, Object> params = new HashMap<>();
        params.put("trip", dto);

        // create json to store params
        StringWriter writer = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(writer, dto);
        } catch (IOException e) {
            LOGGER.error("Error converting dto to JSON: " + dto, e);
        }
        String paramsJson = writer.toString();

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
        emailNotification = emailNotificationRepository.save(emailNotification);

        // later move email send logic to an async process
        String subject = emailTemplate.getSubjectTemplate();
        String content = templateTranslator.translate(emailTemplate.getTemplate(), params);

        try {
            sendEmail(emailNotification, subject, content);
            emailNotification.setStatus(Status.Success);

        } catch (RuntimeException e) {
            LOGGER.error("Error while sending email: " + emailNotification);
            emailNotification.setStatus(Status.Failed);
        }

        emailNotification = emailNotificationRepository.save(emailNotification);
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