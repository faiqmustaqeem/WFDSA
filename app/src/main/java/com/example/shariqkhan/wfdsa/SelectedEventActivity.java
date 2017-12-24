package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.example.shariqkhan.wfdsa.Adapter.PaymentsRVAdapter;
import com.example.shariqkhan.wfdsa.Dialog.EventAttendeesDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventDiscussionDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventGalleryDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventPollsDialog;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectedEventActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    @BindView(R.id.llBottomNav)
    LinearLayout llBottomNav;
    @BindView(R.id.llShare)
    LinearLayout llShare;
    @BindView(R.id.fabPolls)
    FloatingActionButton fabPolls;

    GoogleMap myGoogleMap;
    ImageView image;
    ImageView location;
    Button yes;
    Button no;
    ImageView close;

    TextView textView17;

    public static boolean checkedIn = false;

    TextView address;
    TextView tvCityCountry;
    String latlng;
    String start;
    String end;

    String id;
ImageView ivshare, ivattendees, ivdiscussion, ivgallery, ivcheck;

    Animation shareSlideUp, shareSlideDown;
    private BottomNavigationViewEx bottomNavigationViewEx;
    TextView tvRegister;
    public String URL = "http://codiansoft.com/wfdsa/apis/Event/EventDetail?";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        id = getIntent().getStringExtra("eventid");


        ButterKnife.bind(this);

        initUI();
        ivshare = (ImageView) findViewById(R.id.ivShare);
        ivattendees = (ImageView) findViewById(R.id.ivAttendees);
        ivdiscussion = (ImageView)findViewById(R.id.ivDiscussion);
        ivgallery= (ImageView)findViewById(R.id.ivGallery);
        ivcheck = (ImageView)findViewById(R.id.ivLocation);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        close = (ImageView) findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectedEventActivity.this, "Event Added Seccuessfully!", Toast.LENGTH_SHORT).show();
            }
        });
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

//        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_bar);
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.enableItemShiftingMode(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.setTextVisibility(false);

       // textView17 = (TextView) findViewById(R.id.textView17);
        address = (TextView) findViewById(R.id.address);
        tvCityCountry = (TextView) findViewById(R.id.tvCityCountry);

        Task task = new Task();
        task.execute();

        ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llShare.getVisibility() == View.GONE) {
                            llShare.setVisibility(View.VISIBLE);
                            tvRegister.setEnabled(false);
                            llShare.startAnimation(shareSlideUp);

                        } else {
                            llShare.startAnimation(shareSlideDown);
                            tvRegister.setEnabled(true);
                        }

            }
        });

        ivattendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventAttendeesDialog attendeesDialog = new EventAttendeesDialog(SelectedEventActivity.this);
                        attendeesDialog.show();
            }
        });
        ivcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkedIn){
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
                            checkedIn = true;
                        }
                    });


                    dialog.show();
                }else
                    {
                        Toast.makeText(SelectedEventActivity.this, "You are already checked in!", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        ivgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventGalleryDialog eventGalleryDialog = new EventGalleryDialog(SelectedEventActivity.this, id);
                        eventGalleryDialog.show();
            }
        });
        ivdiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDiscussionDialog d = new EventDiscussionDialog(SelectedEventActivity.this);
                        d.show();
            }
        });
        //NavViewHelper.enableNavigation(SelectedEventActivity.this, bottomNavigationViewEx);

//
//        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//                    case R.id.share:
//
//                        if (llShare.getVisibility() == View.GONE) {
//                            llShare.setVisibility(View.VISIBLE);
//                            tvRegister.setEnabled(false);
//                            llShare.startAnimation(shareSlideUp);
//
//                        } else {
//                            llShare.startAnimation(shareSlideDown);
//                            tvRegister.setEnabled(true);
//                        }
//                        break;
//
//                    case R.id.check:
//
//                        final Dialog dialog = new Dialog(SelectedEventActivity.this);
//                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                        dialog.getWindow().setLayout(lp.width, lp.height);
//                        dialog.setContentView(R.layout.checked_in_dialog);
//
//                        // View view = LayoutInflater.from(SelectedEventActivity.this).inflate(R.layout.checked_in_dialog, null);
//                        TextView view1 = dialog.findViewById(R.id.Acceptance);
//                        TextView view2 = dialog.findViewById(R.id.Rejection);
//                        view2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        view1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                                Toast.makeText(SelectedEventActivity.this, "Successfully checked in!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//                        dialog.show();
//
//                        break;
//
//                    case R.id.discussion:
//
//                        EventDiscussionDialog d = new EventDiscussionDialog(SelectedEventActivity.this);
//                        d.show();
//                        break;
//
//                    case R.id.gallery:
//
//                        EventGalleryDialog eventGalleryDialog = new EventGalleryDialog(SelectedEventActivity.this);
//                        eventGalleryDialog.show();
//                        break;
//
//                    case R.id.people:
//                        EventAttendeesDialog attendeesDialog = new EventAttendeesDialog(SelectedEventActivity.this);
//                        attendeesDialog.show();
//                        break;
//
//
//                }
//
//                return false;
//            }
//        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(SelectedEventActivity.this, MainActivity.class);
//                startActivity(i);
        /*Removing bug*/

                finish();
            }
        });


//        location = (ImageView) findViewById(R.id.ivLocation);
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                ivDiscussion.setImageResource(R.drawable.ic_discussion);
//                ivAttendess.setImageResource(R.drawable.ic_attendees);
//                ivGallery.setImageResource(R.drawable.ic_gallery);
//                location.setImageResource(R.drawable.ic_checked_bluee);
//                ivShare.setImageResource(R.drawable.ic_share);
//                //  location.setIma
//                final Dialog dialog = new Dialog(SelectedEventActivity.this);
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                dialog.getWindow().setLayout(lp.width, lp.height);
//                dialog.setContentView(R.layout.checked_in_dialog);
//
//                // View view = LayoutInflater.from(SelectedEventActivity.this).inflate(R.layout.checked_in_dialog, null);
//                TextView view1 = dialog.findViewById(R.id.Acceptance);
//                TextView view2 = dialog.findViewById(R.id.Rejection);
//                view2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Toast.makeText(SelectedEventActivity.this, "Successfully checked in!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                dialog.show();
//            }
//        });
        final ImageView likesView = (ImageView) findViewById(R.id.ivLike);
        final TextView tvLikeQty = (TextView) findViewById(R.id.tvLikesQty);

        likesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesView.setVisibility(View.GONE);
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

//        ivDiscussion.setOnClickListener(this);
//        ivShare.setOnClickListener(this);
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

    @OnClick({R.id.tvGetDirections, R.id.fabPolls})
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

            case R.id.fabPolls:
                EventPollsDialog pollsDialog = new EventPollsDialog(this);
                pollsDialog.show();
                break;
        }
    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL + "event_id=" + id;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Res", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                JSONObject resultObj = jsonObject.getJSONObject("result");
                String getstatus = resultObj.getString("status");

                if (getstatus.equals("success")) {
                    JSONArray rolesArray = resultObj.getJSONArray("data");
                    JSONObject obj = rolesArray.getJSONObject(0);
                    //    roleArray = new String[rolesArray.length()];
//                    tvCityCountry.setText(obj.getString("location"));
//                    address.setText(obj.getString("venue"));


                    //  fetchMyPayments();


                }
                progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SelectedEventActivity.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}