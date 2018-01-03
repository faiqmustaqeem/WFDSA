package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.EventsRVAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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




    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    @BindView(R.id.editText)
    EditText message;


    Double lat;
    Double lng;

    String phNo;


    String ValidComment;


    TextView tvDiscussion;

    ProgressDialog PGdialog;

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

        tvDiscussion = (TextView) findViewById(R.id.tvAddress);


        Task task = new Task();
        task.execute();


        image = (ImageView) findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

/*        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!message.getText().toString().equals("")) {
                    ValidComment = message.getText().toString();
                    TaskToSend taskToSend = new TaskToSend();
                    taskToSend.execute();


                } else {
                    Toast.makeText(ContactActivity.this, "Invalid Comment", Toast.LENGTH_SHORT).show();
                }

            }
        });

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

                AlertDialog alertDialog = new AlertDialog.Builder(ContactActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure you want to call?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                callPhoneNumber();
                            }
                        });
                alertDialog.show();

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
                Uri uri = Uri.parse("geo:" + lat + "," + lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        myGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng dsaLocation = new LatLng(38.9030289, -77.03815029999998);

        googleMap.addMarker(new MarkerOptions()
                .position(dsaLocation)
                .title("Direct Selling Association"));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dsaLocation, 12));
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

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            //String url = "http://codiansoft.com/wfdsa/apis/Announcement/Announcement";

            // Log.e("url", "http://codiansoft.com/wfdsa/apis/Resources/Get_resource");

            String response = getHttpData.getData("http://codiansoft.com/wfdsa/apis/UserGuide/Contact");

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
                    JSONArray resourcesData = resultObj.getJSONArray("data");

                    //    resourcesGroupList = new ArrayList<>(resourcesData.length());


                    //   roleArray = new String[rolesArray.length()];
                    // String idArray[] = new String[rolesArray.length()];

                    for (int i = 0; i < resourcesData.length(); i++) {
                        JSONObject job = resourcesData.getJSONObject(i);

                        lat = Double.valueOf(job.getString("lat"));
                        lng = Double.valueOf(job.getString("lng"));
                        phNo = job.getString("contact_no");
                        tvDiscussion.setText(job.getString("address"));

                    }

                }


                PGdialog.dismiss();
//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();

            }
            PGdialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PGdialog = new ProgressDialog(ContactActivity.this);
            PGdialog.setTitle("Loading Information");
            PGdialog.setCanceledOnTouchOutside(false);
            PGdialog.show();

        }
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phNo));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phNo));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Log.e("NotGranted", "Permission not Granted");
            }
        }
    }

    private class TaskToSend extends AsyncTask<Object, Object, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Object... objects) {
            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ContactActivity.this);
            progressDialog.setTitle("Submitting Response");

            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost("http://codiansoft.com/wfdsa/apis/UserGuide/ContactSubmit?");
            Log.e("Must", "Must");
//
//
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("frm_name", MainActivity.getFirstName + "" + MainActivity.getLastName));
            parameters.add(new BasicNameValuePair("frm_email", MainActivity.getEmail));
            parameters.add(new BasicNameValuePair("message", ValidComment));

            Log.e("message", ValidComment);


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

            JSONObject jsonobj;
            if (s != null) {
                try {
                    jsonobj = new JSONObject(s);
                    Log.e("JSON", s);


                    JSONObject result = jsonobj.getJSONObject("result");
                    String checkResult = result.getString("status");
                    if (checkResult.equals("success")) {
                        Toast.makeText(ContactActivity.this, result.getString("response"), Toast.LENGTH_SHORT).show();
                        message.setText("");

                    } else {
                        // Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage", e.getMessage());

                    progressDialog.dismiss();
                }


            }

            progressDialog.dismiss();
        }

    }
}