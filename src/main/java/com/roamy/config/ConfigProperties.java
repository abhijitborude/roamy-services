package com.roamy.config;

/**
 * Created by Abhijit on 1/10/2016.
 */
public class ConfigProperties {

    private Double maxAllowedRomoney = 500d;

    private String defaultEmailCover = "http://res.cloudinary.com/abhijitab/image/upload/v1462058721/default_email_cover_ox4pz0.jpg";

    public Double getMaxAllowedRomoney() {
        return maxAllowedRomoney;
    }

    public String getDefaultEmailCover() {
        return defaultEmailCover;
    }

    public void setDefaultEmailCover(String defaultEmailCover) {
        this.defaultEmailCover = defaultEmailCover;
    }
}
