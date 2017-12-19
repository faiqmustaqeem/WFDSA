package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class EventsModel {
    String id;
    String eventTitle;
    String eventDescription;
    String venueCountry;
    String venueCity;
    String day;
    String month;
    String year;
    String time;
    String imageUrl;


    String personal;

    public EventsModel() {
    }

    public EventsModel(String id, String eventTitle, String eventDescription, String venueCountry, String venueCity, String day, String month, String year, String time, String personal) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.venueCountry = venueCountry;
        this.venueCity = venueCity;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.personal = personal;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getId() {
        return id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getVenueCountry() {
        return venueCountry;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setVenueCountry(String venueCountry) {
        this.venueCountry = venueCountry;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
