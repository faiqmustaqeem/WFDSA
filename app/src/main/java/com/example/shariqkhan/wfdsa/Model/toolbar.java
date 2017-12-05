package com.example.shariqkhan.wfdsa.Model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.R;


public class toolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

    }
}
