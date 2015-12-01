package com.roamy.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by Abhijit on 11/30/2015.
 */
public class RoamyUtils {

    // generate number string e.g. 731216
    public static String generateRandomNumericString(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    // generate alphanumeric string e.g. 9bb72p
    public static String generateRandomAlphanumbericString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}
