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

    public AnnouncementsModel() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
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
