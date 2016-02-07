package com.roamy.service.notification.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roamy.dao.api.EmailNotificationRepository;
import com.roamy.dao.api.EmailTemplateRepository;
import com.roamy.domain.EmailNotification;
import com.roamy.domain.EmailTemplate;
import com.roamy.domain.Reservation;
import com.roamy.domain.TripInstance;
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
        emailNotification = emailNotificationRepository.save(emailNotification);

        // later move email send logic to an async process
        String email = templateTranslator.translate(emailTemplate.getTemplate(), params);
        LOGGER.info("sending {}", email);
    }
}
