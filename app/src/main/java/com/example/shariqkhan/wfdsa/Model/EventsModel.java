package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class EventsModel {
    String id, eventTitle, eventDescription, venueCountry, venueCity, day, month, year, time;

    public EventsModel(String id, String eventTitle, String eventDescription, String venueCountry, String venueCity, String day, String month, String year, String time) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.venueCountry = venueCountry;
        this.venueCity = venueCity;
        this.day = day;
        this.month = month;
        this.year = day;
        this.time = time;
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
}
