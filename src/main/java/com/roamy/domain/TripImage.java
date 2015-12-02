package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 12/1/2015.
 */
@Embeddable
public class TripImage {

    @Column(name = "CAPTION", length = DbConstants.MEDIUM_TEXT)
    private String caption;

    @NotNull
    @Column(name = "URL", length = DbConstants.LONG_TEXT)
    private String url;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TripImage{" +
                "caption='" + caption + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
