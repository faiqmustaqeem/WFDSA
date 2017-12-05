package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnnouncementsActivity extends AppCompatActivity {
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    AnnouncementsRVAdapter announcementsRVAdapter;

    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ANNOUNCEMENT");
        ImageView image;
        image = (ImageView)findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        announcementsRVAdapter = new AnnouncementsRVAdapter(this, GlobalClass.announcementsList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
        rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
        rvAnnouncements.setAdapter(announcementsRVAdapter);




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

        }
        return true;
    }
}