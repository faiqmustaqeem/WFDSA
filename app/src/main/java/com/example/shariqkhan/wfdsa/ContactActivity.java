package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap myGoogleMap;
    @BindView(R.id.tvGetDirections)
    TextView tvGetDirections;

    @BindView(R.id.clLocation)
    ConstraintLayout clLocation;
    @BindView(R.id.clCall)
    ConstraintLayout clCall;
    @BindView(R.id.clMessage)
    ConstraintLayout clMessage;
    @BindView(R.id.ivLocation)
    ImageView ivLocation;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.ivMessage)
    ImageView ivMessage;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        ivMessage.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("CONTACT US");


        image = (ImageView)findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

/*        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick({R.id.tvGetDirections, R.id.ivLocation, R.id.ivCall, R.id.ivMessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLocation:
                ivLocation.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                ivCall.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                ivMessage.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clLocation.setVisibility(View.VISIBLE);
                clCall.setVisibility(View.GONE);
                clMessage.setVisibility(View.GONE);
                break;
            case R.id.ivCall:
                ivCall.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                ivLocation.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                ivMessage.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clCall.setVisibility(View.VISIBLE);
                clLocation.setVisibility(View.GONE);
                clMessage.setVisibility(View.GONE);
                break;
            case R.id.ivMessage:
                ivMessage.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                ivCall.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                ivLocation.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clMessage.setVisibility(View.VISIBLE);
                clCall.setVisibility(View.GONE);
                clLocation.setVisibility(View.GONE);
                break;
            case R.id.tvGetDirections:
                Uri uri = Uri.parse("geo:" + 38.903210 + "," + -77.038123);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        myGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng dsaLocation = new LatLng(38.903210, -77.038123);

        googleMap.addMarker(new MarkerOptions()
                .position(dsaLocation)
                .title("Direct Selling Association"));
        myGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(dsaLocation , 14.0f) );
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