package com.roamy.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Abhijit on 11/30/2015.
 */
public class RoamyUtils {

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
}
