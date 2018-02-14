package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.EventsRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.MainResourceAdapter;
import com.example.shariqkhan.wfdsa.Adapter.ResourcesRVadapter;
import com.example.shariqkhan.wfdsa.Adapter.TopThreeResourcesAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.example.shariqkhan.wfdsa.Model.MainResourceModel;
import com.example.shariqkhan.wfdsa.Model.TopThreeModel;
import com.example.shariqkhan.wfdsa.custom.RecyclerTouchListener;
import com.example.shariqkhan.wfdsa.custom.ResourcesGroup;
import com.example.shariqkhan.wfdsa.custom.ResourcesSubItems;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int INTENT_CONSTANT_GALLERY = 1;
    public static String getFirstName;
    public static String getLastName;
    public static String getId;
    public static String DECIDER = "";
    public static String stype;
    public static String imageUrl = "";
    static String password;
    static String getCountry;
    static String getEmail;
    static String phoneNo;
    public ArrayList<EventsModel> arrayList = new ArrayList<>();
    public ArrayList<AnnouncementsModel> arrayList2 = new ArrayList<>();
    public ArrayList<TopThreeModel> topThreeModelArrayList = new ArrayList<>();
    ImageView ivSignOut;
    ImageView ivSettings;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.ivWFDSALogo)
    ImageView ivWFDSALogo;
    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;
    String[] titlesArray;
    String[] resource_permission;
    String[] resources_id;
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    @BindView(R.id.tvViewAllAnnouncements)
    TextView tvViewAllAnnouncements;
    @BindView(R.id.tvViewAllEvents)
    TextView tvViewAllEvents;
    @BindView(R.id.tvViewAllResources)
    TextView tvViewAllResources;
//    @BindView(R.id.tvAdvocacy)
//    TextView tvAdvocacy;
//    @BindView(R.id.tvAssociationService)
//    TextView tvAssociationService;
//    @BindView(R.id.tvGlobalRegulatory)
//    TextView tvGlobalRegulatory;

    @BindView(R.id.rvResources)
    RecyclerView rvRespurces;
    EventsRVAdapter eventsRVAdapter;
    AnnouncementsRVAdapter announcementsRVAdapter;
    CircleImageView ivUserPic;
    TextView tvUserName;
    TextView textView1;
    SharedPreferences prefs;
    String refreshedToken;
    Button b1;

    ProgressDialog PGdialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_CONSTANT_GALLERY) {


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Twitter.initialize(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        FacebookSdk.sdkInitialize(getApplicationContext());


//        refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("Token", refreshedToken);
//        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
//        editor.putString("myToken", refreshedToken);
//        editor.apply();
//
//
//        Task6 task6 = new Task6();
//        task6.execute();
        initUI();


//



        prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        getFirstName = prefs.getString("first_name", "");
        getLastName = prefs.getString("last_name", "");
        password = prefs.getString("password", "");
        phoneNo = prefs.getString("contact_no", "");
        getCountry = prefs.getString("country", "");
        getEmail = prefs.getString("email", "");
        DECIDER = prefs.getString("type", "");
        stype = prefs.getString("stype", "");
        getId = prefs.getString("deciderId", "");
        GlobalClass.member_role = prefs.getString("role", "");
        Log.e("role", GlobalClass.member_role);


        imageUrl = prefs.getString("image", "");

        Log.e("api_sec", prefs.getString("api_secret", ""));
        Log.e("first_name", getFirstName);
        Log.e("last_name", getLastName);
        Log.e("email", getEmail);
        Log.e("contact_no", phoneNo);
        Log.e("password", password);
        Log.e("type", DECIDER);
        Log.e("imageUrl", imageUrl);


//        ivUserPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent galleryIntent = new Intent();
//                galleryIntent.setType("image/*");
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(galleryIntent,INTENT_CONSTANT_GALLERY);
//            }
//        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View layout = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(layout);

        ivUserPic = (CircleImageView) layout.findViewById(R.id.ivUserPic);
        tvUserName = (TextView) layout.findViewById(R.id.tvUserName);
        textView1 = (TextView) layout.findViewById(R.id.textView1);


        Task task = new Task();
        task.execute();

        Task2 task2 = new Task2();
        task2.execute();


        if (stype.equals("fb")) {

            tvUserName.setText("Facebook user");
            textView1.setVisibility(View.GONE);

        } else {

            tvUserName.setText(getFirstName + " " + getLastName);
            textView1.setText(getEmail);
        }

        if (ProfileActivity.uri != null)
            ivUserPic.setImageURI(ProfileActivity.uri);


        try {
            Glide.with(this).load(imageUrl).into(ivUserPic);
        } catch (Exception e) {
            ivUserPic.setImageResource(R.drawable.ic_user);
            Log.e("PicsetMessage", e.getMessage());
        }

        ivSignOut = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivSignOut);
        ivSettings = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivSettings);
        ivSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /*SharedPreferences settings = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("apiSecretKey", "");
                                editor.putString("userID", "");
                                editor.commit();*/

                                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                                dialog.dismiss();
                                ActivityCompat.finishAffinity(MainActivity.this);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                MainActivity.this.startActivity(logoutIntent);
                                LoginManager.getInstance().logOut();

                                SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                                SharedPreferences.Editor edit = preferences.edit();
                                edit.clear();
                                edit.commit();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                    finish();


            }
        });


      //  Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivWFDSALogo);
        //   Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2017/08/BAS_23341.jpg").into(ivImage);


        rvAnnouncements.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, rvAnnouncements, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //   Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, AnnouncementsActivity.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        rvEvents.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, rvEvents, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EventsModel eventsModel = arrayList.get(position);
                Log.e("role", GlobalClass.member_role);
                if (MainActivity.DECIDER.equals("member")) {
                    if (eventsModel.getPersonal().contains(GlobalClass.member_role)) {
                        Intent i = new Intent(MainActivity.this, SelectedEventActivity.class);
                        Log.e("eventid", eventsModel.getId());
                        i.putExtra("eventid", eventsModel.getId());
                        startActivity(i);
                    } else if (eventsModel.getPersonal().equals("Public")) {
                        Intent i = new Intent(MainActivity.this, SelectedEventActivity.class);
                        Log.e("eventid", eventsModel.getId());
                        i.putExtra("eventid", eventsModel.getId());
                        startActivity(i);


                    } else {
                        Toast.makeText(MainActivity.this, "You dont have access to this event!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (eventsModel.getPersonal().equals("Public")) {
                        Intent i = new Intent(MainActivity.this, SelectedEventActivity.class);
                        i.putExtra("eventid", eventsModel.getId());
                        startActivity(i);


                    } else {

                        Toast.makeText(MainActivity.this, "Members Access Only!", Toast.LENGTH_SHORT).show();
                    }

                }


            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Task5 task5 = new Task5();
        task5.execute();


    }

    private void sendToken(String refreshedToken) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Token", refreshedToken)
                .build();


        Request request = new Request.Builder()
                .url(GlobalClass.base_url+"wfdsa/apis/Event/Events_Notification")
                .post(body)
                .build();


        try {
            client.newCall(request).execute();
            // Toast.makeText(this, "Hello from fbidclass", Toast.LENGTH_SHORT).show();

            Log.e("Paappppiii", "Inside Paapiu");
        } catch (IOException e) {
            Log.e("ExceptionOfToken", e.getMessage());
            e.printStackTrace();
        }
    }

    private void initUI() {
//        eventsRVAdapter = new EventsRVAdapter(MainActivity.this, new ArrayList<EventsModel>(GlobalClass.eventsList.subList(1, ((int) GlobalClass.eventsList.size() / 2))));
//        RecyclerView.LayoutManager mEventLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvEvents.setLayoutManager(mEventLayoutManager);
//        rvEvents.setItemAnimator(new DefaultItemAnimator());
//        rvEvents.setAdapter(eventsRVAdapter);
//
//        announcementsRVAdapter = new AnnouncementsRVAdapter(this, new ArrayList<AnnouncementsModel>(GlobalClass.announcementsList.subList(1, ((int) GlobalClass.announcementsList.size() / 2))));
//        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
//        rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
//        rvAnnouncements.setAdapter(announcementsRVAdapter);


    }

    @OnClick({R.id.ivImage, R.id.tvViewAllAnnouncements, R.id.tvViewAllEvents, R.id.tvViewAllResources})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivImage:
                // Toast.makeText(this, "Image Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvViewAllAnnouncements:
                Intent i = new Intent(this, AnnouncementsActivity.class);
                startActivity(i);
                break;
            case R.id.tvViewAllEvents:
                Intent i2 = new Intent(this, AllEventsActivity.class);
                startActivity(i2);
                break;
            case R.id.tvViewAllResources:
                Intent i3 = new Intent(this, MainResources.class);
                startActivity(i3);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_events) {
            Intent eventsIntent = new Intent(MainActivity.this, AllEventsActivity.class);
            MainActivity.this.startActivity(eventsIntent);

        } else if (id == R.id.nav_resources) {
            Intent resourcesIntent = new Intent(MainActivity.this, MainResources.class);
            MainActivity.this.startActivity(resourcesIntent);
        } else if (id == R.id.nav_my_payment) {
            Intent paymentIntent = new Intent(MainActivity.this, MyPaymentActivity.class);
            MainActivity.this.startActivity(paymentIntent);

        } else if (id == R.id.nav_announcement) {
            Intent contactIntent = new Intent(MainActivity.this, AnnouncementsActivity.class);
            MainActivity.this.startActivity(contactIntent);
        } else if (id == R.id.nav_about_wfdsa) {

            Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
            MainActivity.this.startActivity(aboutIntent);

        } else if (id == R.id.nav_about_wfdsa_leadership) {


            Intent intent = new Intent(MainActivity.this, LeaderShipActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_dsa_members) {
            Intent intent = new Intent(MainActivity.this, MemberActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact_us) {
            Intent contactIntent = new Intent(MainActivity.this, ContactActivity.class);
            MainActivity.this.startActivity(contactIntent);

        } else if (id == R.id.nav_app_user_guide) {
            Intent intent = new Intent(MainActivity.this, UserGuideActivity.class);
            startActivity(intent);

        } else if (id == R.id.nae_committees) {
            {
                Intent intent = new Intent(MainActivity.this, CommiteesActivity.class);
                startActivity(intent);
        }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void checkForPermissions()
    {

    }

    private String getJson() {
        HttpClient httpClient = new DefaultHttpClient();
        String urltoSend = "";

        HttpPost post = new HttpPost(urltoSend);

        StringBuilder buffer = new StringBuilder();

        List<NameValuePair> pairs = new ArrayList<>();

        pairs.add(new BasicNameValuePair("", ""));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs);
            post.setEntity(encodedFormEntity);
            HttpResponse res = httpClient.execute(post);

            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));
            String Line = "";
            while ((Line = reader.readLine()) != null) {
                buffer.append(Line);

            }

            reader.close();

        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingException", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("ClientException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }


        return buffer.toString();
    }

    private class Task6 extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] params) {

            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost(GlobalClass.base_url + "wfdsa/apis/Event/Events_Notification");
            Log.e("Must", "Must");

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("token", refreshedToken));
//            parameters.add(new BasicNameValuePair("last_name", lastName));
//            parameters.add(new BasicNameValuePair("email", email));
//            parameters.add(new BasicNameValuePair("country", getItem));
//            parameters.add(new BasicNameValuePair("contact", contactNum));
//            parameters.add(new BasicNameValuePair("password", password));
//            parameters.add(new BasicNameValuePair("confirm_password", confirmPassword));
//
//            Log.e("f", firstName);
//            Log.e("l", lastName);
//            Log.e("e", email);
//            Log.e("c", getItem);
//            Log.e("c", contactNum);
//            Log.e("p", password);
//            Log.e("c", confirmPassword);

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
//                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
//                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
//                    Log.e("reader", Line);
//                  Log.e("buffer", buffer.toString());
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

            JSONObject jsonobj;
            if (s != null) {
                try {
                    jsonobj = new JSONObject(s);
                    Log.e("JSON", s);


                    JSONObject result = jsonobj.getJSONObject("result");
                    String checkResult = result.getString("status");

                    if (checkResult.equals("success")) {


                    } else {
                        Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage1", e.getMessage());


                }


            }


        }
    }

    private class Task extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Fetching Events");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String[] params) {

            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost(GlobalClass.base_url+"wfdsa/apis/Event/Events");
            Log.e("Must", "Must");




            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
//                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
//                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    //   Log.e("reader", Line);
                    //  Log.e("buffer", buffer.toString());
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
            eventsRVAdapter = new EventsRVAdapter(MainActivity.this, arrayList);
            LinearLayoutManager mEventLayoutManager = new LinearLayoutManager(getApplicationContext());

            rvEvents.setLayoutManager(mEventLayoutManager);
            rvEvents.setItemAnimator(new DefaultItemAnimator());
            rvEvents.setAdapter(eventsRVAdapter);
            JSONObject jsonobj;
            if (s != null) {
                try {
                    jsonobj = new JSONObject(s);
                    Log.e("JSON", s);


                    JSONObject result = jsonobj.getJSONObject("result");
                    String checkResult = result.getString("status");

                    if (checkResult.equals("success")) {


                        JSONArray data = result.getJSONArray("data");
                        arrayList.clear();
                        for (int i = 0; i < data.length(); i++) {
                            if (i == data.length() - 1) {
                                EventsModel model = new EventsModel();
                                JSONObject job = data.getJSONObject(i);
                                model.setId(job.getString("event_id"));
                                model.setEventTitle(job.getString("title"));
                                model.setVenueCity(job.getString("place"));

                                String sub = job.getString("start_date");


                                String filter = sub.substring(0, 2);

                                model.setDay(filter);

                                model.setMonth(job.getString("start_date").substring(3, 6));
                                model.setYear(job.getString("start_date").substring(6, 10));
                                model.setTime(job.getString("start_date"));
                                //   model.setImageUrl(job.getString("upload_image"));
                                model.setPersonal(job.getString("permission"));


                                arrayList.add(model);
                            }
                        }

                        if(arrayList.size() > 0) {

                            Log.e("list", arrayList.get(0).getEventTitle());


                            // EventsModel model=arrayList.remove(arrayList.size()-1);
                            //  arrayList.add(0,model);
                            //  Collections.reverse(arrayList);
                            eventsRVAdapter.notifyDataSetChanged();
                            Log.e("list", arrayList.get(arrayList.size() - 1).getEventTitle());
//                            eventsRVAdapter = new EventsRVAdapter(MainActivity.this, arrayList);
//                            LinearLayoutManager mEventLayoutManager = new LinearLayoutManager(getApplicationContext());
//
//                            rvEvents.setLayoutManager(mEventLayoutManager);
//                            rvEvents.setItemAnimator(new DefaultItemAnimator());
//                            rvEvents.setAdapter(eventsRVAdapter);
                        }


                    } else {
                        // Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage2", e.getMessage());

                    progressDialog.dismiss();
                }


            }

            progressDialog.dismiss();
        }
    }

    private class Task2 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url+"wfdsa/apis/Announcement/Announcement";

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
                    //   roleArray = new String[rolesArray.length()];
                    // String idArray[] = new String[rolesArray.length()];

                    for (int i = 0; i < rolesArray.length(); i++) {
                        AnnouncementsModel model = new AnnouncementsModel();
                        JSONObject obj = rolesArray.getJSONObject(i);
                        model.setId(obj.getString("announcement_id"));
                        model.setTitle(obj.getString("title"));
                        model.setDescription(obj.getString("announcement_message"));
                        model.setImage(obj.getString("upload_image"));
                        model.setDate(obj.getString("date"));
                        String announce_for = obj.getString("announce_for");

                        if (obj.getString("status").equals("1")) {
                            if (MainActivity.DECIDER.equals("member")) {

                                Boolean conditionSatisfied = false;
                                if (GlobalClass.member_role.contains(",")) {
                                    List<String> splitted_roles = Arrays.asList(GlobalClass.member_role.split(","));

                                    if (splitted_roles != null) {
                                        for (int j = 0; j < splitted_roles.size(); j++) {
                                            if (announce_for.contains(splitted_roles.get(j).toString())) {
                                                conditionSatisfied = true;
                                                break;
                                            }
                                        }
                                    }

                                }

                                if (conditionSatisfied == true) // multiple roles
                                {
                                    arrayList2.add(model);
                                } else if (announce_for.contains(GlobalClass.member_role)) {
                                    arrayList2.add(model);

                                } else if (announce_for.equals("Public")) {
                                    arrayList2.add(model);
                                } else {
                                    // dont add
                                }
                            } else {
                                if (announce_for.equals("Public")) {
                                    arrayList2.add(model);
                                } else {
                                    // dont add
                                }


                            }
                        }
                    }

                    ArrayList<AnnouncementsModel> an_list = new ArrayList<>();
                    an_list.add(arrayList2.get(arrayList2.size() - 1));
                    // Collections.reverse(arrayList2);
                    announcementsRVAdapter = new AnnouncementsRVAdapter(MainActivity.this, an_list,rvAnnouncements);
//                    RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
//                    rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
//                    rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
                    rvAnnouncements.setAdapter(announcementsRVAdapter);

                }



            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();

            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    private class Task5 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {


            String response = getHttpData.getData(GlobalClass.base_url + "wfdsa/apis/Resources/Get_three_resource");

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Res", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                JSONObject resultObj = jsonObject.getJSONObject("result");
                Log.e("top_three", resultObj.toString());
                String getstatus = resultObj.getString("status");

                if (getstatus.equals("success")) {
                    JSONArray resourcesData = resultObj.getJSONArray("data");

                    for (int i = 0; i < resourcesData.length(); i++) {
                        JSONObject job = resourcesData.getJSONObject(i);
                        TopThreeModel model = new TopThreeModel();
                        model.setName(job.getString("title_2"));
                        model.setPath(job.getString("upload_file"));

                        topThreeModelArrayList.add(model);

                    }


                    TopThreeResourcesAdapter adapter = new TopThreeResourcesAdapter(topThreeModelArrayList, MainActivity.this);
                    rvRespurces.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rvRespurces.setAdapter(adapter);


                }


                PGdialog.dismiss();


            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();

            }
            PGdialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PGdialog = new ProgressDialog(MainActivity.this);
            PGdialog.setTitle("Fetching Resources");
            PGdialog.setCanceledOnTouchOutside(false);
            // PGdialog.show();

        }
    }


}
