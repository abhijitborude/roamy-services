package com.roamy.integration.sms.dto;

/**
 * Created by Abhijit on 12/8/2015.
 */
public class SmsResult {

    private String smsClientId;

    private String messageId;

    private boolean success;

    private String mobileNo;

    private String errorCode;

    private String errorDescription;

    public String getSmsClientId() {
        return smsClientId;
    }

    public void setSmsClientId(String smsClientId) {
        this.smsClientId = smsClientId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    @Override
    public String toString() {
        return "SmsResult{" +
                "smsClientId='" + smsClientId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", success=" + success +
                ", mobileNo='" + mobileNo + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
