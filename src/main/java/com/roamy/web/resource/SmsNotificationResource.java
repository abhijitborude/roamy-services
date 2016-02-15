package com.roamy.web.resource;

import com.roamy.dao.api.SmsNotificationRepository;
import com.roamy.domain.SmsNotification;
import com.roamy.domain.Status;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("sms-notification")
public class SmsNotificationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsNotificationResource.class);

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Find SMS notifications", notes = "Finds SMS notifications by phone number and status. " +
            "Pagination is enabled and can be controlled using two parameters- page and size." +
            "By default first page is displayed with 100 elements (page=0, size=100). " +
            " Actual result is contained in the data field of the response.")
    public RestResponse findSmsNotifications(@ApiParam(value = "Phone number", required = true)
                                                @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                             @ApiParam(value = "status [PENDING/SUCCESS/FAILED]", required = false)
                                                @RequestParam(value = "status", required = false) String status,
                                             @ApiParam(value = "Page number", defaultValue = "0", required = false)
                                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @ApiParam(value = "Page size", defaultValue = "100", required = false)
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
