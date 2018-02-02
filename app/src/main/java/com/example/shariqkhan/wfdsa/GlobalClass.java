package com.example.shariqkhan.wfdsa;

import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.EventsModel;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class GlobalClass {
    public static ArrayList<EventsModel> eventsList;
    public static ArrayList<AnnouncementsModel> announcementsList;
    public static String selected_resource="";
    public static String selelcted_event="";
    public static String selelcted_event_id="";
    public static String selelcted_event_fees = "";
    public static String base_url="http://sastiride.com/";
    public static String selected_event_name = "";
    public static String selected_event_location = "";
    public static String selected_event_date = "";
    public static String member_role = "";
    public static boolean isAlreadyRegistered = false;
}
