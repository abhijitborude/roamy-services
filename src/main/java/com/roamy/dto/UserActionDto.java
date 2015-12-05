package com.roamy.dto;

/**
 * Created by Abhijit on 12/3/2015.
 */
public class UserActionDto {

    // action should be reset, activate
    private String action;

    private String activationCode;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public String toString() {
        return "UserActionDto{" +
                "action='" + action + '\'' +
                ", activationCode='" + activationCode + '\'' +
                '}';
    }
}
