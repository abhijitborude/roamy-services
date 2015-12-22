package com.roamy.dao.api;

import com.roamy.domain.AlertNotification;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 12/22/2015.
 */
@Repository
public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {

    List<AlertNotification> findByUserIdAndStatusAndExpiryDateGreaterThan(Long userId, Status status, Date date);
}
