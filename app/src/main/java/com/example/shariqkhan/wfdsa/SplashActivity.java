package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.EventsModel;

import java.util.ArrayList;

import static com.example.shariqkhan.wfdsa.GlobalClass.announcementsList;
import static com.example.shariqkhan.wfdsa.GlobalClass.eventsList;


public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        if (isUserLoggedIn()) {
            fetchEvents();
            fetchAnnouncements();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLoggedIn()) {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                } else {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public static void fetchAnnouncements() {
        announcementsList = new ArrayList<AnnouncementsModel>();
        announcementsList.add(new AnnouncementsModel("1", "Announcement text text text text text text text text text text text text text text text text text text text", "Announcement Title", "8th Sep 2017", "http://wfdsa.org/wp-content/uploads/2017/06/France.jpg"));
        announcementsList.add(new AnnouncementsModel("2", "Announcement text text text text text text text text text text text text text text text text text text text", "Announcement Title", "8th Sep 2017", ""));
        announcementsList.add(new AnnouncementsModel("3", "Announcement text text text text text text text text text text text text text text text text text text text", "Announcement Title", "8th Sep 2017", ""));
        announcementsList.add(new AnnouncementsModel("3", "Announcement text text text text text text text text text text text text text text text text text text text", "Announcement Title", "8th Sep 2017", ""));
        announcementsList.add(new AnnouncementsModel("3", "Announcement text text text text text text text text text text text text text text text text text text text", "Announcement Title", "8th Sep 2017", ""));
    }

    public static void fetchEvents() {
        eventsList = new ArrayList<EventsModel>();
        eventsList.add(new EventsModel("1", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY) WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "France", "Paris", "01", "OCT", "2017", "9:00pm"));
        eventsList.add(new EventsModel("2", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY) WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "France", "Paris", "01", "OCT", "2017", "9:00pm"));
        eventsList.add(new EventsModel("3", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY) WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "France", "Paris", "01", "OCT", "2017", "9:00pm"));
        eventsList.add(new EventsModel("3", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY) WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "France", "Paris", "01", "OCT", "2017", "9:00pm"));
        eventsList.add(new EventsModel("3", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "WFDSA CEO COUNCIL MEETING (INVITATION ONLY) WFDSA CEO COUNCIL MEETING (INVITATION ONLY)", "France", "Paris", "01", "OCT", "2017", "9:00pm"));
    }

    private boolean isUserLoggedIn() {
        return false;
    }
}