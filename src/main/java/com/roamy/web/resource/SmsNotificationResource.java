package com.roamy.web.resource;

import com.roamy.dao.api.SmsNotificationRepository;
import com.roamy.domain.SmsNotification;
import com.roamy.domain.Status;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Abhijit on 12/15/2015.
 */
@RestController
@RequestMapping("/smsNotifications")
public class SmsNotificationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsNotificationResource.class);

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RestResponse findSmsNotifications(@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                             @RequestParam(value = "status", required = false) String status,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "100") int size) {

        LOGGER.info("finding sms notifications by phoneNumber({}) and status({})", phoneNumber, status);

        RestResponse response = null;
        try {
            if (!StringUtils.hasText(phoneNumber) && !StringUtils.hasText(status)) {
                throw new RoamyValidationException("Either phone number or status should be provided");
            }

            Status notificationStatus = null;
            if (StringUtils.hasText(status)) {
                notificationStatus = Status.findByText(status);
            }

            List<SmsNotification> notifications = null;

            if (StringUtils.hasText(phoneNumber) && notificationStatus != null) {
                notifications = smsNotificationRepository.findByPhoneNumberAndStatus(phoneNumber, notificationStatus);
            } else if (StringUtils.hasText(phoneNumber)) {
                notifications = smsNotificationRepository.findByPhoneNumber(phoneNumber);
            } else if (notificationStatus != null) {
                notifications = smsNotificationRepository.findByStatus(notificationStatus);
            }

            // TODO: add pagination

            LOGGER.info("Number of sms notifications found: {}", notifications == null ? 0 : notifications.size());

            response = new RestResponse(notifications, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in findSmsNotifications: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
