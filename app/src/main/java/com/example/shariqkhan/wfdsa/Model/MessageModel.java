package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by ShariqKhan on 12/23/2017.
 */

public class MessageModel {
    public String name;
    public String message;
    public String post;
    public String date;
    public String time;

    public MessageModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
