package com.roamy.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roamy.integration.sms.dto.Sms;
import com.roamy.integration.sms.dto.SmsError;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 11/30/2015.
 */
public class RoamyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoamyUtils.class);

    public static final int VERIFICATION_CODE_LENGTH = 6;
    public static final int REFERRAL_CODE_LENGTH = 6;
    public static final int VERIFICATION_CODE_VALIDITY_HOURS = 2;

    public static String generateVerificationCode() {
        return generateRandomNumericString(VERIFICATION_CODE_LENGTH);
    }

    public static String generateReferralCode() {
        return generateRandomAlphanumericString(REFERRAL_CODE_LENGTH);
    }

    public static final Date getVerificationCodeExpiryDate() {
        return new DateTime(new Date()).plusHours(VERIFICATION_CODE_VALIDITY_HOURS).toDate();
    }

    // generate number string e.g. 731216
    public static String generateRandomNumericString(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    // generate alphanumeric string e.g. 9bb72p
    public static String generateRandomAlphanumericString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }

    public static Sms[] convertXmlToSmsList(String xml) throws Exception {
        XmlMapper mapper = new XmlMapper();

        Sms[] smsArray = null;
        if (xml != null) {
            smsArray = mapper.readValue(xml, Sms[].class);
        }

        return smsArray;
    }

    public static SmsError[] convertXmlToSmsErrorList(String xml) throws Exception {
        XmlMapper mapper = new XmlMapper();

        SmsError[] smsErrorArray = null;
        if (xml != null) {
            smsErrorArray = mapper.readValue(xml, SmsError[].class);
        }

        return smsErrorArray;
    }
}
