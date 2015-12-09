package com.roamy.dto;

/**
 * Created by Abhijit on 12/3/2015.
 */
public class UserActionDto {

    // action should be reset, activate
    private String action;

    private String verificationCode;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "UserActionDto{" +
                "action='" + action + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
