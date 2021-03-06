package com.example.shariqkhan.wfdsa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shariqkhan.wfdsa.Dialog.EventAttendeesDialog;
import com.example.shariqkhan.wfdsa.Dialog.EventDiscussionDialog;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Singleton.AppSingleton;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectedEventActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //public static boolean checkedIn = false;
    public static String id;
    public static String pollResponseUrl = GlobalClass.base_url + "wfdsa/apis/Event/Get_Poll?";
    public static boolean isCheckedIn = false;
    public static int photos_upload_left=0;
    int total_seats=0;
    int seats_left=0;
    public boolean isAnswered = false;
    public String AttendeesID;
    public String isLikeable;
    public String Eventname;
    public String loc;
    public String URL = GlobalClass.base_url + "wfdsa/apis/Event/EventDetail?";
    public String eventNameToPass;
    public String locationToSend;
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
    String idKeepTrack[];
    ImageView ivshare, ivattendees, ivdiscussion, ivgallery, ivcheck;
    Animation shareSlideUp, shareSlideDown;
    ImageView ivTwitter, ivLinkedIn, ivFacebook;
    TextView tvRegister;
    CallbackManager callBackManager;
    ImageView likesView;
    TextView tvLikeQty;
    String signintype;
    SupportMapFragment mapFragment;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private ProgressDialog progressDialog;
    private String textonFb;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        if (GlobalClass.isAlreadyRegistered) {
            tvRegister.setText("You are Registered for this Event");
            tvRegister.setClickable(false);
        }

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

        GlobalClass.isAlreadyRegistered = false;
        // isCheckedIn = false;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        if (!prefs.getString("type", "").equals("member"))
        {
            signintype = "2";
        }else{
            signintype = "1";
        }

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
                linkedinIntent.putExtra(Intent.EXTRA_TEXT, textonFb);

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

                SimpleDateFormat df3 = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
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

                Calendar c=Calendar.getInstance();
                c.set(Integer.parseInt(startEventTime.substring(0,2)),Integer.parseInt(startEventTime.substring(3,5)),Integer.parseInt(startEventTime.substring(6,10)));

                long timeInMiliStart = 0; //c.getTimeInMillis();
                long timeInMiliEnd = 0;

                long difference = 0;

                try {
                    d1 = df3.parse(startEventTime);
                    d2 = df3.parse(endEventTime);

                    timeInMiliStart = d1.getTime();
                    timeInMiliEnd = d2.getTime();


                } catch (ParseException e) {
                    Log.e("ParseException", e.getMessage());
                }

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
        mTitle.setText(GlobalClass.selelcted_event);
        image = (ImageView) findViewById(R.id.ivBack);

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


                if (GlobalClass.isAlreadyRegistered) {
                    if (!isCheckedIn) {
                        if (isLocationEnabled(SelectedEventActivity.this)) {
                            Location startPoint = new Location("locationA");
                            startPoint.setLatitude(currentLatitude);
                            startPoint.setLongitude(currentLongitude);

                            Location endPoint = new Location("locationA");
                            endPoint.setLatitude(destinationlat);
                            endPoint.setLongitude(destinationLng);

                            double distance = startPoint.distanceTo(endPoint);

                            if (distance < 1000) // person is near 1 km from event location
                            {
                                // check in api here
                                setCheckedIn();


                            } else {

                                Toast.makeText(SelectedEventActivity.this, "You are not at event location!", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(SelectedEventActivity.this, "Please Enable Location in your settings", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(SelectedEventActivity.this, "You have already checked in!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SelectedEventActivity.this, "You are not registered for this event !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ivgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GlobalClass.isAlreadyRegistered) {
                    Intent intent = new Intent(SelectedEventActivity.this, GalleryActivityMine.class);
                    overridePendingTransition(0, 0);
                    intent.putExtra("Event_id", id);
                    startActivity(intent);
                } else {

                    Toast.makeText(SelectedEventActivity.this, "You are not registered for this event !", Toast.LENGTH_LONG).show();

                }


            }
        });
        ivdiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalClass.isAlreadyRegistered) {
                    Intent intent = new Intent(SelectedEventActivity.this, EventDiscussionDialog.class);
                    overridePendingTransition(0, 0);
                    intent.putExtra("Event_id", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(SelectedEventActivity.this, "You are not registered for this event !", Toast.LENGTH_LONG).show();
                }

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        likesView = (ImageView) findViewById(R.id.ivLike);
        tvLikeQty = (TextView) findViewById(R.id.tvLikesQty);

        likesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //likesView.setVisibility(View.GONE);
                int likes = Integer.parseInt(tvLikeQty.getText().toString());
                tvLikeQty.setText(String.valueOf(likes + 1));
                likesView.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.like_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                likesView.setClickable(false);
                sendLikeData();
            }
        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final Pattern sPattern
                        = Pattern.compile("^[^1-9]+$");
                boolean isZero=sPattern.matcher(GlobalClass.selelcted_event_fees).matches();
                if (isZero) { // match with 0 , 0.0 , 00 , 000 etc
                    // register user without stripe
                    sendData();

                } else {

                    Intent intent = new Intent(SelectedEventActivity.this, RegisterEvent.class);
                    intent.putExtra("name", eventNameToPass);
                    intent.putExtra("location", locationToSend);
                    startActivity(intent);
                }
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

    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }
    public void sendData() {

        final ProgressDialog progressDialog = new ProgressDialog(SelectedEventActivity.this);
        progressDialog.setTitle("Payment in progress");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GlobalClass.base_url + "wfdsa/apis/Payment/payment_verification_noAmount",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            progressDialog.dismiss();

                            JSONObject job = new JSONObject(response);
                            JSONObject result = job.getJSONObject("result");
                            String res = result.getString("response");
                            Log.e("response", res);

                            if (res.equals("Successfully Registered")) {
                                tvRegister.setText("You are Registered for this Event");
                                tvRegister.setClickable(false);
                                GlobalClass.isAlreadyRegistered = true;

                                final Dialog dialog = new Dialog(SelectedEventActivity.this);
                                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                // dialog.setCanceledOnTouchOutside(false);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                dialog.getWindow().setLayout(lp.width, lp.height);
                                dialog.setContentView(R.layout.thanking_activity);

                                TextView event_name = (TextView) dialog.findViewById(R.id.event_name);
                                event_name.setText(GlobalClass.selected_event_name);

                                TextView event_location = (TextView) dialog.findViewById(R.id.event_location);
                                event_location.setText(GlobalClass.selected_event_location);

                                Button button = (Button) dialog.findViewById(R.id.ok);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });


                                TextView event_date = (TextView) dialog.findViewById(R.id.event_date);
                                event_date.setText(GlobalClass.selected_event_date);

                                dialog.setCanceledOnTouchOutside(false);

                                dialog.show();

                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
//                                        finish();
                                    }
                                });
                                // Toast.makeText(RegisterEvent.this, "Payment Transaction Completed!", Toast.LENGTH_SHORT).show();
                                //finish();
                            } else {
                                Toast.makeText(SelectedEventActivity.this, "Payment Transaction Failed!", Toast.LENGTH_SHORT).show();
                                // finish();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            Log.e("ErrorMessage", e.getMessage());
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("Volley_error", error.getMessage());
                        progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            Log.e("Volley_error", error.getMessage());
                        } else {
                            Toast.makeText(SelectedEventActivity.this, "You application is not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

                params.put("api_secret", prefs.getString("api_secret", ""));
                params.put("stripe_token", "0");
                params.put("amount", "0");
                params.put("user_id", MainActivity.getId);
                params.put("signin_type", MainActivity.SIGNIN_TYPE);
                params.put("event_id", GlobalClass.selelcted_event_id);

                Log.e("params", params.toString());

                return params;
            }
        };

        Volley.newRequestQueue(SelectedEventActivity.this).add(request);
        // AppSingleton.getInstance().addToRequestQueue(request, "Payment");
    }

    public void sendLikeData() {
        StringRequest request = new StringRequest(Request.Method.POST, GlobalClass.base_url + "wfdsa/apis/Event/add_likes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject job = new JSONObject(response);
                            JSONObject result = job.getJSONObject("result");
                            String res = result.getString("response");
                            Log.e("response", res);

                            if (res.startsWith("Like Successfully Recieved")) {

                                Log.e("like", res);
                                // Toast.makeText(RegisterEvent.this, "Payment Transaction Completed!", Toast.LENGTH_SHORT).show();
                                //finish();
                            } else {
                                // finish();
                            }
                        } catch (JSONException e) {
                            Log.e("ErrorMessage", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("Volley_error", error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    Log.e("Volley_error", error.getMessage());
                } else {
                    Toast.makeText(SelectedEventActivity.this, "You application is not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", MainActivity.getId);
                params.put("signin_type", MainActivity.SIGNIN_TYPE);
                params.put("event_id", GlobalClass.selelcted_event_id);

                Log.e("params", params.toString());

                return params;
            }
        };

        AppSingleton.getInstance().addToRequestQueue(request);
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
        LatLng dsaLocation = new LatLng(destinationlat, destinationLng);

        googleMap.addMarker(new MarkerOptions()
                .position(dsaLocation)
                .title(locationToSend));
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
                if (isCheckedIn) {
                    if (!isAnswered) {
                        Task2 task2 = new Task2();
                        task2.execute();
                    } else {
                        Toast.makeText(this, "You have already answered this Poll !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Polls are only for checked in users !", Toast.LENGTH_LONG).show();
                }

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
            //  Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
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
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        //When BACK BUTTON is pressed, the activity on the stack is restarted
//        //Do what you want on the refresh procedure here
//
//        Task task = new Task();
//        task.execute();
//    }

    void setCheckedIn() {
        StringRequest request = new StringRequest(Request.Method.POST, GlobalClass.base_url + "wfdsa/apis/Event/Checked_in",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject job = new JSONObject(response);
                            JSONObject result = job.getJSONObject("result");
                            String status = result.getString("status");


                            if (status.equals("success")) {

                                String res = result.getString("response");
                                if (res.equals("User Checked In Successfully.")) {
                                    isCheckedIn = true;
                                    // checkedIn = true;
                                    Toast.makeText(SelectedEventActivity.this, "You have successfully Checked in", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SelectedEventActivity.this, "Checked in failed", Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                // finish();
                            }
                        } catch (JSONException e) {
                            Log.e("ErrorMessage", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Volley_error", error.networkResponse.statusCode + "");
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    Log.e("Volley_error", error.getMessage());
                } else {
                    Toast.makeText(SelectedEventActivity.this, "You application is not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", MainActivity.getId);
                params.put("signin_type", MainActivity.SIGNIN_TYPE);
                params.put("event_id", GlobalClass.selelcted_event_id);

                Log.e("params", params.toString());

                return params;
            }
        };

        AppSingleton.getInstance().addToRequestQueue(request);
    }


    private class Task2 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url+"wfdsa/apis/Event/Get_Poll?" + "event_id=" + id;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                Log.e("Res_poll", s);
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

                                        Task3 task3 = new Task3();
                                        task3.execute();

//                                    final Dialog dialog = new Dialog(SelectedEventActivity.this);
//                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                                    dialog.getWindow().setLayout(lp.width, lp.height);
//                                    dialog.setContentView(R.layout.remarks_dialog);
//
//                                    EditText editText = dialog.findViewById(R.id.etPassword);
//                                    remark = editText.getText().toString();
//                                    TextView cancel = dialog.findViewById(R.id.tvCancel);
//                                    TextView submit = dialog.findViewById(R.id.tvSubmit);
//
//                                    cancel.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    submit.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            Task3 task3 = new Task3();
//                                            task3.execute();
//
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    dialog.show();
//
////


                                        return true;
                                    }

                                })
                                .positiveText("Choose")
                                .show();


                    }
                    else
                    {
                        Toast.makeText(SelectedEventActivity.this, "no poll for this event so far!", Toast.LENGTH_LONG).show();

                    }
// else {
//
//
//                    new LovelyChoiceDialog(SelectedEventActivity.this)
//                            .setTopColorRes(R.color.colorPrimary)
//                            .setTitle(pollQuestion)
//                            .setItemsMultiChoice(choice_array, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
//                                @Override
//                                public void onItemsSelected(List<Integer> positions, List<String> items) {
//                                    for (int i = 0; i < items.size(); i++) {
//
//                                        final String findItByText[] = new String[items.size()];
//                                        findItbyId = new String[positions.size()];
//                                        findItByText[i] = items.get(i);
//
//                                    }
//                                    for (int f = 0; f < positions.size(); f++) {
//
//                                        findItbyId[f] = idKeepTrack[f];
//                                        Log.e("findItById", findItbyId[f]);
//                                    }
//
////                                    final Dialog dialog = new Dialog(SelectedEventActivity.this);
////                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
////
////                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
////                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////
////                                    dialog.getWindow().setLayout(lp.width, lp.height);
////                                    dialog.setContentView(R.layout.remarks_dialog);
////
////                                    EditText editText = dialog.findViewById(R.id.etPassword);
////                                    remark = editText.getText().toString();
////                                    TextView cancel = dialog.findViewById(R.id.tvCancel);
////                                    TextView submit = dialog.findViewById(R.id.tvSubmit);
////
////                                    cancel.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View view) {
////                                            dialog.dismiss();
////                                        }
////                                    });
////                                    submit.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View view) {
////
////                                            Task4 task4 = new Task4();
////                                            task4.execute();
////                                            dialog.dismiss();
////                                        }
////                                    });
////                                    dialog.show();
//                                }
//                            })
//                            .setConfirmButtonText("Confirm")
//                            .show();
//
//                }
//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(SelectedEventActivity.this, "no poll for this event so far!", Toast.LENGTH_LONG).show();
                }

            }
            else {
                    Toast.makeText(SelectedEventActivity.this, "You are not connected to internet !", Toast.LENGTH_LONG).show();
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


            String url = URL + "event_id=" + id + "&user_id=" + MainActivity.getId + "&signin_type=" + MainActivity.SIGNIN_TYPE;

            Log.e("url_selected_event", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {

                Log.e("EventDetailResponse", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);


                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");

                    if (getstatus.equals("success")) {
                        JSONArray rolesArray = resultObj.getJSONArray("data");


                        JSONObject obj = rolesArray.getJSONObject(0);
                        tvAgenda.setText(obj.getString("title"));
                        GlobalClass.selected_event_name = obj.getString("title");

                        eventNameToPass = tvAgenda.getText().toString();

                        // tvAgendaDescription.setText(obj.getString(""));

                        tvSpeakersDetails.setText(obj.getString("speaker"));

                        startEventTime = obj.getString("start_date");
                        endEventTime = obj.getString("end_date");

                        Log.e("start", startEventTime);
                        Log.e("end", endEventTime);

                        GlobalClass.selected_event_date = obj.getString("start_date").substring(0, 10);

                        GlobalClass.selected_event_location = obj.getString("place");

                        GlobalClass.selelcted_event_fees = obj.getString("fee");

                        String noOfPhotosUploaded=obj.getString("photo_uploaded");

                        photos_upload_left=5-Integer.parseInt(noOfPhotosUploaded);

                        total_seats=Integer.parseInt(obj.getString("no_seats"));
                        int seats_registered=Integer.parseInt(obj.getString("total_registered"));
                        seats_left=total_seats-seats_registered;

                        if(seats_left < 1 )
                        {
                            tvRegister.setText("Sorry , Seats Full ");
                            tvRegister.setClickable(false);
                        }

                        tvDayTime.setText(obj.getString("place"));
                        heelo.setText((obj.getString("start_date").substring(0, 10)));
                        address.setText(obj.getString("start_time"));
                        loc = obj.getString("place");
                        tvLikeQty.setText(obj.getString("total_likes"));

                        String agenda=obj.getString("agenda");
                        tvAgendaDescription.setText(agenda);
                        // AttendeesID = obj.getString("attendees_id");

                        isLikeable = String.valueOf(obj.getString("is_like"));
                        if (isLikeable.equals("0")) {
                            Log.e("islike", "0");

                        } else if (isLikeable.equals("1")) {
                            Log.e("islike", "1");
                            // tvLikeQty.setText(String.valueOf(Integer.parseInt(tvLikeQty.getText().toString())+1));
                            likesView.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.like_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                            likesView.setClickable(false);
                        }
                        String is_registered = String.valueOf(obj.getString("is_registered"));

                        if (is_registered.equals("0")) {
                            Log.e("is_registered", is_registered + "");
                            GlobalClass.isAlreadyRegistered = false;
                            ivdiscussion.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.lightGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                            ivcheck.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.lightGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                            ivgallery.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.lightGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                        } else if (is_registered.equals("1")) {
                            Log.e("is_registered", is_registered + "");
                            tvRegister.setText("You are Registered for this Event");
                            tvRegister.setClickable(false);
                            GlobalClass.isAlreadyRegistered = true;
                        }

                        String is_checked_in = String.valueOf(obj.getString("is_checked_in"));
                        Log.e("new_checked_in", is_checked_in);
                        if (is_checked_in.equals("1")) {
                            isCheckedIn = true;
                            ivcheck.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.lightGray), android.graphics.PorterDuff.Mode.MULTIPLY);
//                        ivcheck.setClickable(false);
                        } else if (is_checked_in.equals("0")) {
                            isCheckedIn = false;
                        }


                        String answered = obj.getString("is_answered");
                        if (answered.equals("yes")) {
                            isAnswered = true;
                        } else {
                            isAnswered = false;
                        }

                        destinationlat = Double.parseDouble(obj.getString("latitude"));
                        destinationLng = Double.parseDouble(obj.getString("longitude"));


                        locationToSend = obj.getString("place") + " " + obj.getString("venue");


                        mapFragment.getMapAsync(SelectedEventActivity.this);

                        Eventname = obj.getString("title");
                        tvCityCountry.setText(obj.getString("venue"));


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

            }else {
                Toast.makeText(SelectedEventActivity.this, "You are not connected to internet !", Toast.LENGTH_LONG).show();
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


            HttpPost post = new HttpPost(GlobalClass.base_url + "wfdsa/apis/Event/Add_PollAnswer");
            Log.e("Must", "Must");

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("user_id", MainActivity.getId));
            parameters.add(new BasicNameValuePair("poll_id", pollId));
//            parameters.add(new BasicNameValuePair("remark", remark));
            parameters.add(new BasicNameValuePair("poll_answer_id", idKeep));
            parameters.add(new BasicNameValuePair("member_type", MainActivity.SIGNIN_TYPE));

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    // Log.e("reader", Line);
                    // Log.e("buffer", buffer.toString());
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
            if(s!=null)
            {
                Log.e("Res", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);


                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");

                    if (getstatus.equals("success")) {

                        isAnswered = true;
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

            }else {
                Toast.makeText(SelectedEventActivity.this, "You are not connected to internet !", Toast.LENGTH_LONG).show();
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

    // for multiple choice selection which is removed from requirement
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


            HttpPost post = new HttpPost(GlobalClass.base_url+"wfdsa/apis/Event/Add_PollAnswer?");
            Log.e("Must", "Must");
            String pollAnswerId = "";
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("user_id", MainActivity.getId));
            parameters.add(new BasicNameValuePair("poll_id", pollId));
//            parameters.add(new BasicNameValuePair("remark", remark));
            for (int i = 0; i < findItbyId.length; i++) {
                pollAnswerId += findItbyId[i] + ",";
            }
            String answer = pollAnswerId.substring(0, pollAnswerId.length() - 2);
            Log.e("answer", answer);
            parameters.add(new BasicNameValuePair("poll_answer_id", answer));
            parameters.add(new BasicNameValuePair("member_type", MainActivity.SIGNIN_TYPE));

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