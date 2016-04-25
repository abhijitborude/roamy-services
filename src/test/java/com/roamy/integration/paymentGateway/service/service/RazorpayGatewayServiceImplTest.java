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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 1/14/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class RazorpayGatewayServiceImplTest {

    @Autowired
    @Qualifier("razorpayGatewayService")
    private PaymentGatewayService razorpayGatewayService;

    @Test
    public void testGetPaymentDetails() throws Exception {
        try {
            PaymentDto paymentDto = razorpayGatewayService.getPaymentDetails("pay_5OEpliRJDpr16n");
        } catch (Exception e) {

        }
    }

    @Test
    public void testCapturePayment() throws Exception {
        try {
            PaymentDto paymentDto = razorpayGatewayService.capturePayment("pay_5OEpliRJDpr16n", 7500d);
        } catch (Exception e) {

        }
    }
}