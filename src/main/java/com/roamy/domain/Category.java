package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "CATEGORY", schema = "ROAMY")
public class Category extends CitableEntity {

    @Column(name = "IMAGE_CAPTION", length = DbConstants.MEDIUM_TEXT)
    private String imageCaption;

    @NotNull
    @Column(name = "IMAGE_URL", length = DbConstants.LONG_TEXT)
    private String imageUrl;

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
