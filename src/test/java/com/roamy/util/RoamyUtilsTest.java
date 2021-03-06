package com.roamy.util;

import com.roamy.integration.sms.dto.Sms;
import com.roamy.integration.sms.dto.SmsError;
import org.junit.Test;

/**
 * Created by Abhijit on 12/7/2015.
 */
public class RoamyUtilsTest {

    @Test
    public void testConvertXmlToSmsList() throws Exception {
        Sms[] smsArray = RoamyUtils.convertXmlToSmsList("<smslist><sms><smsclientid>798849234</smsclientid><messageid>1768120065</messageid><mobile-no>+19083919686</mobile-no></sms></smslist>");
        System.out.println(smsArray[0]);

        Sms[] smsArray1 = RoamyUtils.convertXmlToSmsList(null);
        System.out.println(smsArray1);
    }

    @Test
    public void testConvertXmlToSmsErrorList() throws Exception {
        SmsError[] smsErrorArray = RoamyUtils.convertXmlToSmsErrorList("<smslist><error><smsclientid>0</smsclientid><error-code>-10003</error-code><error-description>Invalid Mobile</error-description>" +
                "<mobile-no>11119083919686</mobile-no><error-action>0</error-action></error></smslist>");
        System.out.println(smsErrorArray);

        SmsError[] smsErrorArray1 = RoamyUtils.convertXmlToSmsErrorList(null);
        System.out.println(smsErrorArray1);
    }

    @Test
    public void testGenerateVerificationCode() throws Exception {

    }

    @Test
    public void testGenerateReferralCode() throws Exception {

    }

    @Test
    public void testGenerateToken() throws Exception {

    }

    @Test
    public void testGetVerificationCodeExpiryDate() throws Exception {

    }

    @Test
    public void testGenerateRandomNumericString() throws Exception {

    }

    @Test
    public void testGenerateRandomAlphanumericString() throws Exception {

    }

    @Test
    public void testGetEmailFormattedDate() throws Exception {

    }

    @Test
    public void testGetEmailFormattedCurrency() throws Exception {

    }

    @Test
    public void testPlusDays() throws Exception {

    }

    @Test
    public void testMinusDays() throws Exception {

    }
}