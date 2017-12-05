package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventGalleryModel {
    String id, imageURL;

    public EventGalleryModel(String id, String imageURL) {
        this.id = id;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }
}
