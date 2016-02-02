package com.roamy.integration.sms.service.impl;

import com.roamy.integration.sms.dto.Sms;
import com.roamy.integration.sms.dto.SmsError;
import com.roamy.integration.sms.dto.SmsResult;
import com.roamy.integration.sms.service.api.SmsService;
import com.roamy.util.RoamyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Abhijit on 12/8/2015.
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    private static final String SMS_SERVICE_URI = "http://mainadmin.dove-sms.com/sendsms.jsp?user=Roamy1&password=745745&mobiles={phoneNumber}&sms={text}&senderid=iROAMY";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public SmsResult sendSms(String phoneNumber, String text) {

        LOGGER.info("Sending sms to phoneNumber({}): {}", phoneNumber, text);

        // Do Get SMS service URI
        String response = restTemplate.getForObject(SMS_SERVICE_URI, String.class, phoneNumber, text);
        LOGGER.info("Response received from SmsService: {}", response);

        SmsResult smsResult = new SmsResult();

        try {
            Sms[] smsList = RoamyUtils.convertXmlToSmsList(response);

            if (smsList != null) {
                // success: create result object
                Sms sms = smsList[0];
                smsResult.setSmsClientId(sms.getSmsclientid());
                smsResult.setMessageId(sms.getMessageid());
                smsResult.setMobileNo(sms.getMobileno());
                smsResult.setSuccess(true);

                LOGGER.info("Success sending sms: {}", smsResult);
            } else {
                // error: create result object with error
                smsResult.setErrorCode("0");
                smsResult.setErrorDescription("No response from the SmsService");
                smsResult.setSuccess(false);

                LOGGER.error("Error while sending sms: {}", smsResult);
            }

        } catch (Exception e) {
            // failure: create result from response
            try {
                SmsError[] smsErrors = RoamyUtils.convertXmlToSmsErrorList(response);

                SmsError error = smsErrors[0];

                smsResult.setSmsClientId(error.getSmsclientid());
                smsResult.setErrorCode(error.getErrorCode());
                smsResult.setErrorDescription(error.getErrorDescription());
                smsResult.setMobileNo(error.getMobileNo());
                smsResult.setSuccess(false);
            } catch (Exception e1) {

                smsResult.setErrorCode("0");
                smsResult.setErrorDescription("Invalid response from the SmsService");
                smsResult.setSuccess(false);
            }

            LOGGER.error("Error while sending sms: {}", smsResult);
        }

        return smsResult;
    }
}
