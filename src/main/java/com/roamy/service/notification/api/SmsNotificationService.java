package com.roamy.service.notification.api;

import com.roamy.domain.Reservation;

/**
 * Created by Abhijit on 12/8/2015.
 */
public interface SmsNotificationService {

    void sendVerificationSms(String phoneNumber, String verificationCode);

    void sendTripReservationShareSms(Reservation reservation, String userName, String phoneNumber);
}
