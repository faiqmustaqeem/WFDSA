package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class AnnouncementsModel {
    String id, description, title, date, image;

    public AnnouncementsModel(String id, String description, String title, String date, String image) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.date = date;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getImage() {
        return image;
    }
}
