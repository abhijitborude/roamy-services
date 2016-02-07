package com.roamy.service.notification.api;

import com.roamy.domain.Reservation;

/**
 * Created by Abhijit on 2/2/2016.
 */
public interface EmailNotificationService {

    void sendTripReservationEmail(Reservation reservation);
}
