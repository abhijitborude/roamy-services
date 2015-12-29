package com.roamy.integration.imagelib.dto;

/**
 * Created by Abhijit on 12/28/2015.
 */
public class ImageLibraryIdentifier {

    private String id;

    private String url;

    public ImageLibraryIdentifier() {
    }

    public ImageLibraryIdentifier(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageLibraryIdentifier{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
