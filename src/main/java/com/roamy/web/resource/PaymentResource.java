package com.roamy.web.resource;

import com.roamy.dto.RestResponse;
import com.roamy.integration.paymentGateway.dto.PaymentDto;
import com.roamy.integration.paymentGateway.service.api.PaymentGatewayService;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by Abhijit on 1/23/2016.
 */
@RestController
@RequestMapping("/payments")
public class PaymentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResource.class);

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
    public RestResponse getPaymentDetails(@PathParam("transactionId") String transactionId) {

        LOGGER.info("fetching payment details for transactionId: {}", transactionId);

        RestResponse response = null;

        try {
            if (!StringUtils.hasText(transactionId)) {
                throw new RoamyValidationException("Invalid transactionId: " + transactionId);
            }

            PaymentDto paymentDto = paymentGatewayService.getPaymentDetails(transactionId);
            LOGGER.info("payment details found: {}", paymentDto);

            response = new RestResponse(paymentDto, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getPaymentDetails: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
