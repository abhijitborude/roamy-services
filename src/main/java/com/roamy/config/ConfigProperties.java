package com.roamy.config;

/**
 * Created by Abhijit on 1/10/2016.
 */
public class ConfigProperties {

    public Double signUpBonus = 1000d;

    public Double refferralSignupBonus = 1000d;

    public Double refferralBonus = 1000d;

    private Double maxAllowedRomoney = 500d;

    private String defaultEmailCover = "http://res.cloudinary.com/abhijitab/image/upload/v1462058721/default_email_cover_ox4pz0.jpg";

    public Double getSignUpBonus() {
        return signUpBonus;
    }

    public Double getRefferralSignupBonus() {
        return refferralSignupBonus;
    }

    public Double getRefferralBonus() {
        return refferralBonus;
    }

    public Double getMaxAllowedRomoney() {
        return maxAllowedRomoney;
    }

    public String getDefaultEmailCover() {
        return defaultEmailCover;
    }
}
