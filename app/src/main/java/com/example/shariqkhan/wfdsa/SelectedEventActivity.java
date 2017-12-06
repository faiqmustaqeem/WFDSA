package com.example.shariqkhan.wfdsa;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Dialog.EventAttendeesDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventDiscussionDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventGalleryDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventPollsDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectedEventActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    @BindView(R.id.ivDiscussion)
    ImageView ivDiscussion;
    @BindView(R.id.llBottomNav)
    LinearLayout llBottomNav;
    @BindView(R.id.llShare)
    LinearLayout llShare;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.fabPolls)
    FloatingActionButton fabPolls;
    GoogleMap myGoogleMap;
    ImageView image;

    Animation shareSlideUp, shareSlideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        ButterKnife.bind(this);

        initUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Selected Events");

        image = (ImageView) findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(SelectedEventActivity.this, MainActivity.class);
//                startActivity(i);
        /*Removing bug*/

                finish();
            }
        });


        shareSlideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llShare.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initUI() {
        shareSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.share_slide_up);
        shareSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.share_slide_down);

        llBottomNav.bringToFront();
        ivDiscussion.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        myGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng dsaLocation = new LatLng(38.903210, -77.038123);

        googleMap.addMarker(new MarkerOptions()
                .position(dsaLocation)
                .title("Direct Selling Association"));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dsaLocation, 14.0f));
    }

    @OnClick({R.id.tvGetDirections, R.id.ivShare, R.id.ivDiscussion, R.id.ivGallery, R.id.ivAttendees, R.id.ivLocation, R.id.fabPolls})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLocation:
            case R.id.tvGetDirections:
                try {
                    Uri uri = Uri.parse("geo:" + 38.903210 + "," + -77.038123);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Please install Google Maps", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivShare:
                if (llShare.getVisibility() == View.GONE) {
                    llShare.setVisibility(View.VISIBLE);
                    llShare.startAnimation(shareSlideUp);

                } else {
                    llShare.startAnimation(shareSlideDown);

                }
                break;
            case R.id.ivDiscussion:
                EventDiscussionDialog d = new EventDiscussionDialog(this);
                d.show();
                break;
            case R.id.ivGallery:
                EventGalleryDialog eventGalleryDialog = new EventGalleryDialog(this);
                eventGalleryDialog.show();
                break;
            case R.id.ivAttendees:
                EventAttendeesDialog attendeesDialog = new EventAttendeesDialog(this);
                attendeesDialog.show();
                break;
            case R.id.fabPolls:
                EventPollsDialog pollsDialog = new EventPollsDialog(this);
                pollsDialog.show();
                break;
        }
    }
}