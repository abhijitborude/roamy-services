package com.roamy.integration.sms.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by Abhijit on 12/7/2015.
 */
@JacksonXmlRootElement(localName = "error")
public class SmsError {

    @JacksonXmlProperty(localName = "smsclientid")
    private String smsclientid;

    @JacksonXmlProperty(localName = "error-code")
    private String errorCode;

    @JacksonXmlProperty(localName = "error-description")
    private String errorDescription;

    @JacksonXmlProperty(localName = "mobile-no")
    private String mobileNo;

    @JacksonXmlProperty(localName = "error-action")
    private String errorAction;

    public String getSmsclientid() {
        return smsclientid;
    }

    public void setSmsclientid(String smsclientid) {
        this.smsclientid = smsclientid;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(String errorAction) {
        this.errorAction = errorAction;
    }

    @Override
    public String toString() {
        return "SmsError{" +
                "smsclientid='" + smsclientid + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", errorAction='" + errorAction + '\'' +
                '}';
    }
}
