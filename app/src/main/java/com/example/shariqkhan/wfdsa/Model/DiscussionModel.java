package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 11/4/2017.
 */

public class DiscussionModel {
    String id, image, senderName, senderDescription, comment, date, time;

    public DiscussionModel(String id, String senderName, String senderDescription, String image, String comment, String date, String time) {
        this.id = id;
        this.senderName = senderName;
        this.senderDescription = senderDescription;
        this.comment = comment;
        this.image = image;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderDescription() {
        return senderDescription;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

}
