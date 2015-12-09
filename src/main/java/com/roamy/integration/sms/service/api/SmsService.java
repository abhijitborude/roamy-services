package com.roamy.integration.sms.service.api;

import com.roamy.integration.sms.dto.SmsResult;

/**
 * Created by Abhijit on 12/7/2015.
 */
public interface SmsService {

    SmsResult sendSms(String phoneNumber, String text);
}
