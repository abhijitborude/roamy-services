package com.roamy.service.notification.api;

import com.roamy.domain.Reservation;
import com.roamy.domain.SmsNotification;

/**
 * Created by Abhijit on 12/8/2015.
 */
public interface SmsNotificationService {

    SmsNotification sendVerificationSms(String phoneNumber, String verificationCode);

    SmsNotification sendTripReservationShareSms(Reservation reservation, String userName, String phoneNumber);
}
