package com.roamy.integration.paymentGateway.service.service;

import com.roamy.TestApplication;
import com.roamy.integration.paymentGateway.dto.PaymentDto;
import com.roamy.integration.paymentGateway.service.api.PaymentGatewayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 1/14/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class RazorpayGatewayServiceImplTest {

    @Autowired
    @Qualifier("razorpayGatewayService")
    private PaymentGatewayService razorpayGatewayService;

    @Test
    public void testGetPaymentDetails() throws Exception {
        PaymentDto paymentDto = razorpayGatewayService.getPaymentDetails("pay_4nPf8p0K6OXPWy");
    }

    @Test
    public void testCapturePayment() throws Exception {
        PaymentDto paymentDto = razorpayGatewayService.capturePayment("pay_4nPf8p0K6OXPWy", 7500d);
    }
}