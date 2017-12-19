package com.example.shariqkhan.wfdsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.EventsRVAdapter;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.example.shariqkhan.wfdsa.custom.RecyclerTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView ivSignOut;
    ImageView ivSettings;

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.ivWFDSALogo)
    ImageView ivWFDSALogo;

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;

    @BindView(R.id.tvViewAllAnnouncements)
    TextView tvViewAllAnnouncements;

    @BindView(R.id.tvViewAllEvents)
    TextView tvViewAllEvents;

    @BindView(R.id.tvViewAllResources)
    TextView tvViewAllResources;

    @BindView(R.id.tvAdvocacy)
    TextView tvAdvocacy;
    @BindView(R.id.tvAssociationService)
    TextView tvAssociationService;
    @BindView(R.id.tvGlobalRegulatory)
    TextView tvGlobalRegulatory;

    EventsRVAdapter eventsRVAdapter;
    AnnouncementsRVAdapter announcementsRVAdapter;
    CircleImageView ivUserPic;
    TextView tvUserName;
    TextView textView1;
    SharedPreferences prefs;

    static String getFirstName;
    static String getLastName;
    static String password;
    static String getCountry;
    static String getEmail;
    static String phoneNo;
    public static final int INTENT_CONSTANT_GALLERY = 1;
    public static String DECIDER = "";

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


//
        prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        getFirstName = prefs.getString("first_name", "");
        getLastName = prefs.getString("last_name", "");
        password = prefs.getString("password", "");
        phoneNo = prefs.getString("contact_no", "");
        getCountry = prefs.getString("country", "");
        getEmail = prefs.getString("email", "");
        DECIDER = prefs.getString("type", "");

        Log.e("first_name", getFirstName);
        Log.e("last_name", getLastName);
        Log.e("email", getEmail);
        Log.e("contact_no", phoneNo);
        Log.e("password", password);
        Log.e("type", DECIDER);


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


        tvUserName.setText(getFirstName + " " + getLastName);
        textView1.setText(getEmail);
        if (ProfileActivity.uri != null)
            ivUserPic.setImageURI(ProfileActivity.uri);

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
                if (MainActivity.DECIDER.equals("member")) {
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Only members can edit profiles!", Toast.LENGTH_LONG).show();
                }

            }
        });


        initUI();

        Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivWFDSALogo);
        Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2017/08/BAS_23341.jpg").into(ivImage);


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
                EventsModel eventsModel = GlobalClass.eventsList.get(position);
                // Toast.makeText(MainActivity.this, eventsModel.getId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, SelectedEventActivity.class);
                startActivity(i);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initUI() {
        eventsRVAdapter = new EventsRVAdapter(MainActivity.this, new ArrayList<EventsModel>(GlobalClass.eventsList.subList(1, ((int) GlobalClass.eventsList.size() / 2))));
        RecyclerView.LayoutManager mEventLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvEvents.setLayoutManager(mEventLayoutManager);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvEvents.setAdapter(eventsRVAdapter);

        announcementsRVAdapter = new AnnouncementsRVAdapter(this, new ArrayList<AnnouncementsModel>(GlobalClass.announcementsList.subList(1, ((int) GlobalClass.announcementsList.size() / 2))));
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
        rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
        rvAnnouncements.setAdapter(announcementsRVAdapter);


    }

    @OnClick({R.id.ivImage, R.id.tvViewAllAnnouncements, R.id.tvViewAllEvents, R.id.tvViewAllResources, R.id.tvAdvocacy, R.id.tvAssociationService, R.id.tvGlobalRegulatory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivImage:
                Toast.makeText(this, "Image Clicked", Toast.LENGTH_SHORT).show();
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
            case R.id.tvAdvocacy:
            case R.id.tvAssociationService:
            case R.id.tvGlobalRegulatory:
                Intent i3 = new Intent(this, MyResourcesActivity.class);
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
            Intent resourcesIntent = new Intent(MainActivity.this, MyResourcesActivity.class);
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
            Intent intent = new Intent(MainActivity.this, CommiteesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
