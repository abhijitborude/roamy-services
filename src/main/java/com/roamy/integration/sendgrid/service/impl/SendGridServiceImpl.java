package com.roamy.integration.sendgrid.service.impl;

import com.roamy.integration.sendgrid.dto.SendGridEmailDto;
import com.roamy.integration.sendgrid.service.api.SendGridService;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Abhijit on 2/13/2016.
 */
@Service("sendGridService")
public class SendGridServiceImpl implements SendGridService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridServiceImpl.class);

    private static final String API_KEY = "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";

    @Override
    public void sendEmail(SendGridEmailDto emailDto) {
        LOGGER.info("sending email: {}", emailDto);
        SendGrid sendgrid = new SendGrid(API_KEY);

        SendGrid.Email email = new SendGrid.Email();
        emailDto.getToAddresses().forEach(e -> email.addTo(e));
        emailDto.getCcAddresses().forEach(e -> email.addCc(e));
        emailDto.getBccAddresses().forEach(e -> email.addBcc(e));
        email.setFrom(emailDto.getFromAddress());
        email.addCategory(emailDto.getCategory());
        email.setReplyTo(emailDto.getReplyToAddress());
        email.setSubject(emailDto.getSubject());
        email.setHtml(emailDto.getHtml());

        try {
            SendGrid.Response response = sendgrid.send(email);
            LOGGER.info("response: {}", response.getMessage());

            if (!response.getStatus()) {
                throw new RuntimeException("Error while sending email: " + response.getMessage());
            }
        } catch (SendGridException e) {
            LOGGER.error("error while sending email: {}", e);
            throw new RuntimeException(e);
        }
    }
}
