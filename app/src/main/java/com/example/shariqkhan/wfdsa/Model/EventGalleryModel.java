package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventGalleryModel {
    String id;
    private String imageURL;


    public EventGalleryModel(String id, String imageURL) {
        this.id = id;
        this.setImageURL(imageURL);

    }

    public EventGalleryModel() {

    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
