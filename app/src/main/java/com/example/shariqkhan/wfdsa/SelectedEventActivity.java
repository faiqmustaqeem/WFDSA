package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
    @BindView(R.id.ivGallery)
    ImageView ivGallery;
    @BindView(R.id.ivAttendees)
    ImageView ivAttendess;

    GoogleMap myGoogleMap;
    ImageView image;
    ImageView location;
    Button yes;
    Button no;

    Animation shareSlideUp, shareSlideDown;

    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        ButterKnife.bind(this);

        initUI();
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        location = (ImageView) findViewById(R.id.ivLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                

                ivDiscussion.setImageResource(R.drawable.ic_discussion);
                ivAttendess.setImageResource(R.drawable.ic_attendees);
                ivGallery.setImageResource(R.drawable.ic_gallery);
                location.setImageResource(R.drawable.ic_checked_bluee);
                ivShare.setImageResource(R.drawable.ic_share);
                //  location.setIma
                final Dialog dialog = new Dialog(SelectedEventActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(lp.width, lp.height);
                dialog.setContentView(R.layout.checked_in_dialog);

                // View view = LayoutInflater.from(SelectedEventActivity.this).inflate(R.layout.checked_in_dialog, null);
                TextView view1 = dialog.findViewById(R.id.Acceptance);
                TextView view2 = dialog.findViewById(R.id.Rejection);
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(SelectedEventActivity.this, "Successfully checked in!", Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.show();
            }
        });
        final ImageView likesView = (ImageView) findViewById(R.id.ivLike);
        final TextView tvLikeQty = (TextView) findViewById(R.id.tvLikesQty);

        likesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesView.setVisibility(View.INVISIBLE);
                tvLikeQty.setText("422");
            }
        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedEventActivity.this, RegisterEvent.class);
                startActivity(intent);

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

    @OnClick({R.id.tvGetDirections, R.id.ivShare, R.id.ivDiscussion, R.id.ivGallery, R.id.ivAttendees, R.id.fabPolls})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ivLocation:
//
//
//
//
//
//                Toast.makeText(this, "You Are Checked In !!", Toast.LENGTH_SHORT).show();
//                break;

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
                ivDiscussion.setImageResource(R.drawable.ic_discussion);
                ivAttendess.setImageResource(R.drawable.ic_attendees);
                ivGallery.setImageResource(R.drawable.ic_gallery);
                location.setImageResource(R.drawable.ic_checked);

                ivShare.setImageResource(R.drawable.ic_share_share);


                if (llShare.getVisibility() == View.GONE) {
                    llShare.setVisibility(View.VISIBLE);
                    tvRegister.setEnabled(false);
                    llShare.startAnimation(shareSlideUp);

                } else {
                    llShare.startAnimation(shareSlideDown);
                    tvRegister.setEnabled(true);
                }
                break;

            case R.id.ivDiscussion:
                location.setImageResource(R.drawable.ic_checked);
                Resources  res = getResources();

                ivDiscussion.setImageDrawable(res.getDrawable(R.drawable.ic_discussion_blue));

                //ivDiscussion.setImageResource(R.drawable.ic_discussion_blue);
                ivAttendess.setImageResource(R.drawable.ic_attendees);
                ivGallery.setImageResource(R.drawable.ic_gallery);

                ivShare.setImageResource(R.drawable.ic_share);


                EventDiscussionDialog d = new EventDiscussionDialog(this);
                d.show();
                break;

            case R.id.ivGallery:
                location.setImageResource(R.drawable.ic_checked);
                ivDiscussion.setImageResource(R.drawable.ic_discussion);
                ivAttendess.setImageResource(R.drawable.ic_attendees);
                ivGallery.setImageResource(R.drawable.ic_gallery_blue);

                ivShare.setImageResource(R.drawable.ic_share);

                EventGalleryDialog eventGalleryDialog = new EventGalleryDialog(this);
                eventGalleryDialog.show();
                break;

            case R.id.ivAttendees:
                location.setImageResource(R.drawable.ic_checked);
                ivDiscussion.setImageResource(R.drawable.ic_discussion);
                ivAttendess.setImageResource(R.drawable.ic_attendees_blue);
                ivGallery.setImageResource(R.drawable.ic_gallery);

                ivShare.setImageResource(R.drawable.ic_share);

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