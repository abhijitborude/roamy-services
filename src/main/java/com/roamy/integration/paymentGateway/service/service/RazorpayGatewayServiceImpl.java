package com.roamy.integration.paymentGateway.service.service;

import com.roamy.integration.paymentGateway.dto.PaymentDto;
import com.roamy.integration.paymentGateway.service.api.PaymentGatewayService;
import com.roamy.util.RoamyException;
import com.roamy.util.RoamyValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * Created by Abhijit on 1/12/2016.
 */
@Service("razorpayGatewayService")
public class RazorpayGatewayServiceImpl implements PaymentGatewayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RazorpayGatewayServiceImpl.class);

    @Value("${razorpay.key-id}:${razorpay.key-secret}")
    private String key;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() {
        return "https://api.razorpay.com/v1";
    }

    private String getAuthHeader() {
        byte[] bytes = Base64.getEncoder().encode(key.getBytes());
        return "Basic " + new String(bytes);
    }

    @Override
    public PaymentDto getPaymentDetails(String transactionId) {

        if (!StringUtils.hasText(transactionId)) {
            throw new RoamyValidationException("Invalid transaction id");
        }

        LOGGER.info("fetching payment details for transactionId: {}", transactionId);

        String url = getBaseUrl() + "/payments/" + transactionId;
        LOGGER.info(url);

        PaymentDto paymentDto = null;
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", getAuthHeader());

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<PaymentDto> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.GET, entity, PaymentDto.class);

            paymentDto = responseEntity.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating URI");
        }
        LOGGER.info("Response received from Razorpay: {}", paymentDto);

        return paymentDto;
    }

    @Override
    public PaymentDto capturePayment(String transactionId, Double amount) {

        if (!StringUtils.hasText(transactionId)) {
            throw new RoamyValidationException("Invalid transaction id");
        } else if (amount == null || amount <= 0d) {
            throw new RoamyValidationException("Invalid amount: " + amount);
        }

        LOGGER.info("capturing payment for transactionId: {} and amount: {}", transactionId, amount);

        String url = getBaseUrl() + "/payments/" + transactionId + "/capture";
        LOGGER.info(url);

        PaymentDto paymentDto = null;
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", getAuthHeader());

            long amountToCapture = amount.longValue() * 100;
            StringBuilder sb = new StringBuilder("{\"amount\":").append(amountToCapture).append("}");

            HttpEntity<String> entity = new HttpEntity<>(sb.toString(), headers);
            PaymentDto responseEntity = restTemplate.postForObject(new URI(url), entity, PaymentDto.class);

        } catch (URISyntaxException e) {
            LOGGER.error("Error while calling Razorpay: ", e);
            throw new RuntimeException("Error creating URI");

        } catch (HttpClientErrorException ex) {
            LOGGER.error("Error while calling Razorpay: ", ex);

            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RoamyValidationException("Invalid transactionId (" + transactionId + ") or amount (" + amount + ")");
            }
            throw new RoamyException("Error while capturing payment: " + ex.getMessage());

        } catch (Throwable t) {
            LOGGER.error("Error while calling Razorpay: ", t);
            throw new RoamyException("Error while capturing payment: " + t.getMessage());
        }

        LOGGER.info("Response received from Razorpay: {}", paymentDto);

        return paymentDto;
    }
}
