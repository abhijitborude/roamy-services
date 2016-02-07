package com.roamy.dao.api;

import com.roamy.domain.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 2/3/2016.
 */
@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

}
