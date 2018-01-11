package com.example.shariqkhan.wfdsa;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.Adapter.PaymentsRVAdapter;
import com.example.shariqkhan.wfdsa.Dialog.EventAttendeesDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventDiscussionDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventGalleryDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventPollsDialog;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectedEventActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.llBottomNav)
    LinearLayout llBottomNav;
    @BindView(R.id.llShare)
    LinearLayout llShare;
    @BindView(R.id.fabPolls)
    FloatingActionButton fabPolls;

    GoogleMap myGoogleMap;
    ImageView image;
    ImageView location;
    String choice;
    String pollQuestion;
    Button yes;
    Button no;
    ImageView close;
    String findItbyId[];
    String pollId;
    TextView textView17;

    public static boolean checkedIn = false;

    TextView address;
    TextView heelo;
    TextView tvDayTime;
    TextView tvCityCountry;

    String choice_array[];
    ShareDialog dialog;
    URL url;
    String pollResponse;

    String textToPost;
    String startEventTime;
    String endEventTime;

    double destinationlat;
    double destinationLng;
    String remark;


    String idKeep;
    TextView tvAgenda;
    TextView tvAgendaDescription;
    TextView tvEventDescription;
    TextView tvSpeakersDetails;
    TextView tvHostsDetails;
    public static String id;
    String idKeepTrack[];

    public String AttendeesID;

    public String isLikeable;
    public boolean isCheckedIn;
    ImageView ivshare, ivattendees, ivdiscussion, ivgallery, ivcheck;

    public String Eventname;
    public String loc;

    Animation shareSlideUp, shareSlideDown;
    private BottomNavigationViewEx bottomNavigationViewEx;
    ImageView ivTwitter, ivLinkedIn, ivFacebook;

    TextView tvRegister;
    public String URL = "http://codiansoft.com/wfdsa/apis/Event/EventDetail?";
    private ProgressDialog progressDialog;

    public static String pollResponseUrl = " http://codiansoft.com/wfdsa/apis/Event/Get_Poll?";

    CallbackManager callBackManager;
    private String textonFb;

    public String eventNameToPass;

    public String locationToSend;
    ImageView likesView;
    TextView tvLikeQty;


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("InsideActivityResult", "Result");
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        Log.e("Uri", imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        currentItem = currentItem + 1;
                    }
                } else if (data.getData() != null) {
                    String imagePath = data.getData().getPath();
                    Log.e("Uri", imagePath);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        id = getIntent().getStringExtra("eventid");
        pollResponseUrl = pollResponseUrl + "event_id=" + id;

        ButterKnife.bind(this);

        address = (TextView) findViewById(R.id.address);
        tvAgenda = (TextView) findViewById(R.id.tvAgenda);
        tvAgendaDescription = (TextView) findViewById(R.id.tvAgendaDescription);
        tvEventDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvSpeakersDetails = (TextView) findViewById(R.id.tvSpeakersDetails);
        tvHostsDetails = (TextView) findViewById(R.id.tvHostsDetails);

        heelo = (TextView) findViewById(R.id.heelo);
        tvDayTime = (TextView) findViewById(R.id.tvDayTime);
        tvCityCountry = (TextView) findViewById(R.id.tvCityCountry);

        initUI();

        ivshare = (ImageView) findViewById(R.id.ivShare);
        ivattendees = (ImageView) findViewById(R.id.ivAttendees);
        ivdiscussion = (ImageView) findViewById(R.id.ivDiscussion);
        ivgallery = (ImageView) findViewById(R.id.ivGallery);
        ivcheck = (ImageView) findViewById(R.id.ivLocation);

        ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivLinkedIn = (ImageView) findViewById(R.id.ivLinkedIn);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); //

        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    url = new URL("https://https://wfdsa.org");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                TweetComposer.Builder build = new TweetComposer.Builder(SelectedEventActivity.this);
                build.text(textToPost).
                        url(url);
                build.show();

            }
        });


        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallbackManager.Factory.create();
                Intent linkedinIntent = new Intent(Intent.ACTION_SEND);
                linkedinIntent.setType("text/plain");
                linkedinIntent.putExtra(Intent.EXTRA_TEXT, textToPost);
                linkedinIntent.putExtra(Intent.EXTRA_SUBJECT, "https://wfdsa.org");

                boolean linkedinAppFound = false;
                List<ResolveInfo> matches2 = getPackageManager()
                        .queryIntentActivities(linkedinIntent, 0);

                for (ResolveInfo info : matches2) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith(
                            "com.facebook")) {
                        linkedinIntent.setPackage(info.activityInfo.packageName);
                        linkedinAppFound = true;
                        break;
                    }
                }

                if (linkedinAppFound) {
                    startActivity(linkedinIntent);
                } else {
                    Toast.makeText(SelectedEventActivity.this, "Facebook app not Insatlled in your mobile", Toast.LENGTH_SHORT).show();
                }

//                ShareDialog dialog = new ShareDialog(SelectedEventActivity.this);
//
//                ShareLinkContent shareLink = new ShareLinkContent.Builder()
//                        .setQuote("Visit website for details")
//                        .setContentUrl(Uri.parse("https://https://wfdsa.org"))
//                        .build();
//                if (dialog.canShow(ShareLinkContent.class)) {
//                   // Toast.makeText(SelectedEventActivity.this, "Hello inside facebook", Toast.LENGTH_SHORT).show();
//                    dialog.show(shareLink);
//                }

            }
        });

        ivLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkedinIntent = new Intent(Intent.ACTION_SEND);
                linkedinIntent.setType("text/plain");
                linkedinIntent.putExtra(Intent.EXTRA_TEXT, textToPost);

                boolean linkedinAppFound = false;
                List<ResolveInfo> matches2 = getPackageManager()
                        .queryIntentActivities(linkedinIntent, 0);

                for (ResolveInfo info : matches2) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith(
                            "com.linkedin")) {
                        linkedinIntent.setPackage(info.activityInfo.packageName);
                        linkedinAppFound = true;
                        break;
                    }
                }

                if (linkedinAppFound) {
                    startActivity(linkedinIntent);
                } else {
                    Toast.makeText(SelectedEventActivity.this, "LinkedIn app not Insatlled in your mobile", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvRegister = (TextView) findViewById(R.id.tvRegister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        close = (ImageView) findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                String st = df3.format(startEventTime);
//                String en = df3.format(endEventTime);
                Date date = new Date();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Date d1 = null;
                Date d2 = null;
                long elapsedDays = 0;
                long elapsedMinutes = 0;
                long elapsedSeconds = 0;
                long elapsedHours = 0;
                long different = 0;

                long timeInMiliStart = 0;
                long timeInMiliEnd = 0;

                long difference = 0;

                try {
                    d1 = df3.parse(startEventTime);
                    d2 = df3.parse(endEventTime);

                    timeInMiliStart = d1.getTime();
                    timeInMiliEnd = d2.getTime();
//
//                    Log.e("timeStartMili", String.valueOf(timeInMiliStart));
//                    Log.e("timeEndMili", String.valueOf(timeInMiliEnd));
//
//
//                    different = d2.getTime() - d1.getTime();
//                    difference = different;
//                    Log.e("Difference", String.valueOf(different));
//
//                    long secondsInMilli = 1000;
//                    long minutesInMilli = secondsInMilli * 60;
//                    long hoursInMilli = minutesInMilli * 60;
//                    long daysInMilli = hoursInMilli * 24;
//
//                    Log.e("seconds", String.valueOf(secondsInMilli));
//                    Log.e("seconds", String.valueOf(minutesInMilli));
//                    Log.e("seconds", String.valueOf(hoursInMilli));
//                    Log.e("seconds", String.valueOf(daysInMilli));
//
//
//                    elapsedDays = different / daysInMilli;
//                    different = different % daysInMilli;
//
//                    elapsedHours = different / hoursInMilli;
//                    different = different % hoursInMilli;
//
//                    elapsedMinutes = different / minutesInMilli;
//                    different = different % minutesInMilli;
//
//                    elapsedSeconds = different / secondsInMilli;
//
//
//                    Log.e("elDay", String.valueOf(elapsedDays));
//                    Log.e("elHours", String.valueOf(elapsedHours));
//                    Log.e("elMinutes", String.valueOf(elapsedMinutes));
//                    Log.e("elSeconds", String.valueOf(elapsedSeconds));

                } catch (ParseException e) {
                    Log.e("ParseException", e.getMessage());
                }


//
//                Log.e("startTime", startEventTime);
//                Log.e("endTime", endEventTime);
//                Log.e("calendarGetTimeInstance", String.valueOf(cal.getTimeInMillis()));
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", timeInMiliStart);
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", timeInMiliEnd);
                intent.putExtra("title", tvAgenda.getText().toString());
                startActivity(intent);

                //     Toast.makeText(SelectedEventActivity.this, "Event Added Seccuessfully!", Toast.LENGTH_SHORT).show();
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

                if (!isCheckedIn) {
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

                            if (currentLatitude == destinationlat && currentLongitude == destinationLng) {
                                dialog.dismiss();
                                Toast.makeText(SelectedEventActivity.this, "Successfully checked in!", Toast.LENGTH_SHORT).show();
                                checkedIn = true;

                                StringRequest req = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                            } else {
                                Toast.makeText(SelectedEventActivity.this, "You are not on the event location!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                    dialog.show();
                } else {
                    Toast.makeText(SelectedEventActivity.this, "You are already checked in!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ivgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GridView gridView;
//                EventGalleryGVadapter gridAdapter;
//                ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
//                ImageView imageView;
//                FloatingActionButton floatingActionButton;
//                Dialog dialog = new Dialog(SelectedEventActivity.this);
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                dialog.setContentView(R.layout.event_gallery_dialog);
//
//                gridView = (GridView) dialog.findViewById(R.id.gridView);
//                imageView = (ImageView) dialog.findViewById(R.id.close);
//
//                imagesList.add(new EventGalleryModel("1", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//                imagesList.add(new EventGalleryModel("2", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//                imagesList.add(new EventGalleryModel("3", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//                imagesList.add(new EventGalleryModel("4", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//                imagesList.add(new EventGalleryModel("5", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//                imagesList.add(new EventGalleryModel("6", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//                imagesList.add(new EventGalleryModel("7", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//                imagesList.add(new EventGalleryModel("8", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//                imagesList.add(new EventGalleryModel("9", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//
//                gridAdapter = new EventGalleryGVadapter(SelectedEventActivity.this, R.layout.event_gallery_item, imagesList);
//                gridView.setAdapter(gridAdapter);
//
//                floatingActionButton = (FloatingActionButton)dialog.findViewById(R.id.fabAddImage);
//
//                floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
////**These following line is the important one!
//                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 1);
//                    }
//                });
//
//
//                dialog.show();

//                EventGalleryDialog dialog = new EventGalleryDialog(SelectedEventActivity.this, id);
//                dialog.show();

                Intent intent = new Intent(SelectedEventActivity.this, GalleryActivityMine.class);
                overridePendingTransition(0, 0);
                intent.putExtra("Event_id", id);
                startActivity(intent);


            }
        });
        ivdiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedEventActivity.this, EventDiscussionDialog.class);
                overridePendingTransition(0, 0);
                intent.putExtra("Event_id", id);
                startActivity(intent);
//                EventDiscussionDialog d = new EventDiscussionDialog(SelectedEventActivity.this);
//                d.show();
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
        likesView = (ImageView) findViewById(R.id.ivLike);
        tvLikeQty = (TextView) findViewById(R.id.tvLikesQty);

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
                intent.putExtra("name", eventNameToPass);
                intent.putExtra("location", locationToSend);
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
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", currentLatitude, currentLongitude, destinationlat, destinationLng);
                    Uri URI = Uri.parse(uri);
                    Log.e("SendToMapUri", String.valueOf(URI));
                    Intent intent = new Intent(Intent.ACTION_VIEW, URI);
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
                Task2 task2 = new Task2();
                task2.execute();


//                EventPollsDialog pollsDialog = new EventPollsDialog(this);
//                pollsDialog.show();
//                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

//                    if (location == null) {
//                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//
//                    } else {
//                        //If everything went fine lets get latitude and longitude
//                        currentLatitude = location.getLatitude();
//                        currentLongitude = location.getLongitude();
//
//                        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
//                    }

                }

            } else {
                Log.e("NotGranted", "Permission not Granted");
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SelectedEventActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Log.e("current LatLng", currentLatitude + " " + currentLongitude);
            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }


    private class Task2 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = "http://codiansoft.com/wfdsa/apis/Event/Get_Poll?" + "event_id=" + id;

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
                    JSONObject rolesObj = resultObj.getJSONObject("data");
                    JSONObject Obj0 = rolesObj.getJSONObject("0");

                    pollId = Obj0.getString("poll_id");
                    choice = Obj0.getString("choice_selection");
                    pollQuestion = Obj0.getString("poll_question");

                    JSONArray choiceArray = rolesObj.getJSONArray("poll_answer");
                    JSONArray idArray = rolesObj.getJSONArray("id");

                    choice_array = new String[choiceArray.length()];

                    idKeepTrack = new String[idArray.length()];

                    for (int i = 0; i < choiceArray.length(); i++) {

                        choice_array[i] = choiceArray.getString(i);
                        idKeepTrack[i] = idArray.getString(i);
//
//                        choice_array[i] = choiceArray.getString(Integer.parseInt(idKeepTrack[i]));
//                        Log.e("Choices", choice_array[i]);
                    }

                }

                progressDialog.dismiss();

                if (choice.equals("Single Choice")) {
                    new MaterialDialog.Builder(SelectedEventActivity.this)
                            .title(pollQuestion)
                            .items(choice_array)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                    pollResponse = charSequence.toString();
                                    idKeep = idKeepTrack[i];
                                    Log.e("id", idKeep);
                                    Log.e("answer", choice_array[i]);

                                    final Dialog dialog = new Dialog(SelectedEventActivity.this);
                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                    dialog.getWindow().setLayout(lp.width, lp.height);
                                    dialog.setContentView(R.layout.remarks_dialog);

                                    EditText editText = dialog.findViewById(R.id.etPassword);
                                    remark = editText.getText().toString();
                                    TextView cancel = dialog.findViewById(R.id.tvCancel);
                                    TextView submit = dialog.findViewById(R.id.tvSubmit);

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    submit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Task3 task3 = new Task3();
                                            task3.execute();

                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();

//


                                    return true;
                                }

                            })
                            .positiveText("Choose")
                            .show();


                } else {


                    new LovelyChoiceDialog(SelectedEventActivity.this)
                            .setTopColorRes(R.color.colorPrimary)
                            .setTitle(pollQuestion)
                            .setItemsMultiChoice(choice_array, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                                @Override
                                public void onItemsSelected(List<Integer> positions, List<String> items) {
                                    for (int i = 0; i < items.size(); i++) {

                                        final String findItByText[] = new String[items.size()];
                                        findItbyId = new String[positions.size()];
                                        findItByText[i] = items.get(i);

                                    }
                                    for (int f = 0; f < positions.size(); f++) {

                                        findItbyId[f] = idKeepTrack[f];
                                        Log.e("findItById", findItbyId[f]);
                                    }

                                    final Dialog dialog = new Dialog(SelectedEventActivity.this);
                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                    dialog.getWindow().setLayout(lp.width, lp.height);
                                    dialog.setContentView(R.layout.remarks_dialog);

                                    EditText editText = dialog.findViewById(R.id.etPassword);
                                    remark = editText.getText().toString();
                                    TextView cancel = dialog.findViewById(R.id.tvCancel);
                                    TextView submit = dialog.findViewById(R.id.tvSubmit);

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    submit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Task4 task4 = new Task4();
                                            task4.execute();
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            })
                            .setConfirmButtonText("Confirm")
                            .show();

                }


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

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL + "event_id=" + id + "&user_id=" + MainActivity.getId + "&signin_type=" + LoginActivity.decider;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("EventDetailResponse", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                JSONObject resultObj = jsonObject.getJSONObject("result");
                String getstatus = resultObj.getString("status");

                if (getstatus.equals("success")) {
                    JSONArray rolesArray = resultObj.getJSONArray("data");


                    JSONObject obj = rolesArray.getJSONObject(0);
                    tvAgenda.setText(obj.getString("title"));

                    eventNameToPass = tvAgenda.getText().toString();

                    // tvAgendaDescription.setText(obj.getString(""));

                    tvSpeakersDetails.setText(obj.getString("speaker"));

                    startEventTime = obj.getString("start_date");
                    endEventTime = obj.getString("end_date");

                    Log.e("start", startEventTime);
                    Log.e("end", endEventTime);
                    tvDayTime.setText(obj.getString("start_time"));
                    heelo.setText((obj.getString("start_date").substring(0, 10)));
                    address.setText(obj.getString("place"));
                    loc = obj.getString("place");
                    tvLikeQty.setText(obj.getString("total_likes"));

                    AttendeesID = obj.getString("attendees_id");

                    isLikeable = obj.getString("is_like");
                    if (isLikeable.equals("1")) {
                        //hide button
                        likesView.setVisibility(View.GONE);

                    }

                    if (obj.getString("checked_in").equals("1")) {
                        isCheckedIn = true;
                    }else
                        {
                            isCheckedIn = false;
                        }

                    destinationlat = Double.parseDouble(obj.getString("latitude"));
                    destinationLng = Double.parseDouble(obj.getString("longitude"));

                    locationToSend = obj.getString("place") + " " + obj.getString("venue");


                    Eventname = obj.getString("title");
                    tvCityCountry.setText(obj.getString("venue"));
                    if (tvCityCountry.getText().toString().equals("")) {
                        tvCityCountry.setText("Washington,America");
                    }
                    textToPost = tvAgenda.getText().toString()
                            + " event will be held on \n" + startEventTime.substring(0, 11) + " in " + obj.getString("venue")
                            + " at " + loc + " "
                            + obj.getString("speaker")
                            + "will be speaker." + "";

                    textonFb = tvAgenda.getText().toString()
                            + " event will be held on \n" + startEventTime + "\n" + obj.getString("speaker")
                            + " will be speaker." + " \n";
                }
                progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("EventDetailError", e.getMessage());
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

    private class Task3 extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String[] params) {

            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost("http://codiansoft.com/wfdsa/apis/Event/Add_PollAnswer?");
            Log.e("Must", "Must");

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("user_id", MainActivity.getId));
            parameters.add(new BasicNameValuePair("poll_id", pollId));
            parameters.add(new BasicNameValuePair("remark", remark));
            parameters.add(new BasicNameValuePair("poll_answer_id", idKeep));

            parameters.add(new BasicNameValuePair("member_type", LoginActivity.decider));

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    Log.e("reader", Line);
                    Log.e("buffer", buffer.toString());
                    buffer.append(Line);

                }
                reader.close();

            } catch (Exception o) {
                Log.e("Error", o.getMessage());

            }
            return buffer.toString();
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
                    Toast.makeText(SelectedEventActivity.this, "Your Response Submitted!", Toast.LENGTH_SHORT).show();
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
            progressDialog.setTitle("Submitting Response");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    private class Task4 extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String[] params) {

            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost("http://codiansoft.com/wfdsa/apis/Event/Add_PollAnswer?");
            Log.e("Must", "Must");
            String pollAnswerId = "";
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("user_id", MainActivity.getId));
            parameters.add(new BasicNameValuePair("poll_id", pollId));
            parameters.add(new BasicNameValuePair("remark", remark));
            for (int i = 0; i < findItbyId.length; i++) {
                pollAnswerId += findItbyId[i] + ",";
            }
            parameters.add(new BasicNameValuePair("poll_answer_id", pollAnswerId.substring(0, pollAnswerId.length() - 1)));
            parameters.add(new BasicNameValuePair("member_type", LoginActivity.decider));

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    Log.e("reader", Line);
                    Log.e("buffer", buffer.toString());
                    buffer.append(Line);

                }
                reader.close();

            } catch (Exception o) {
                Log.e("Error", o.getMessage());

            }
            return buffer.toString();
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
                    Toast.makeText(SelectedEventActivity.this, "Your Response Submitted!", Toast.LENGTH_SHORT).show();
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
            progressDialog.setTitle("Submitting Response");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

}