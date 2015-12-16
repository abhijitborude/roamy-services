package com.roamy.dao.api;

import com.roamy.domain.SmsNotification;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Abhijit on 12/15/2015.
 */
public interface SmsNotificationRepository extends JpaRepository<SmsNotification, Long> {

    List<SmsNotification> findByStatus(Status status);

    List<SmsNotification> findByPhoneNumber(String phoneNumber);

    List<SmsNotification> findByPhoneNumberAndStatus(String phoneNumber, Status status);
}
