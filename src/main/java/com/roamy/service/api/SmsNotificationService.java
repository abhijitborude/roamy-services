package com.roamy.service.api;

/**
 * Created by Abhijit on 12/8/2015.
 */
public interface SmsNotificationService {

    void sendVerificationSms(String phoneNumber, String verificationCode);
}
