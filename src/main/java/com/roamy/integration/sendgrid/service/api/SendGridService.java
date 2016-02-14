package com.roamy.integration.sendgrid.service.api;

import com.roamy.integration.sendgrid.dto.SendGridEmailDto;

/**
 * Created by Abhijit on 2/13/2016.
 */
public interface SendGridService {

    void sendEmail(SendGridEmailDto emailDto);
}
