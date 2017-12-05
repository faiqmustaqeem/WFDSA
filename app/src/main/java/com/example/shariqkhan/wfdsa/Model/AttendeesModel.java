package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class AttendeesModel {
    String id, name, description, imageURL;

    public AttendeesModel(String id, String name, String description, String imageURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
}
