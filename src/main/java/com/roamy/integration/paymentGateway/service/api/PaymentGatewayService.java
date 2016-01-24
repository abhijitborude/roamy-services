package com.roamy.integration.paymentGateway.service.api;

import com.roamy.integration.paymentGateway.dto.PaymentDto;

/**
 * Created by Abhijit on 1/12/2016.
 */
public interface PaymentGatewayService {

    PaymentDto getPaymentDetails(String transactionId);

    PaymentDto capturePayment(String transactionId, Double amount);
}
