package com.example.shariqkhan.wfdsa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {


    ImageView ivLogo;

    ImageView ivBack;

    Activity activity;

    @BindView(R.id.about_text_new)
    TextView about_text;
    @BindView(R.id.contact_number)
    TextView contact_number;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.address)
    TextView address;


    @BindView(R.id.textView16)
    EditText clickMeToViewUrl;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        activity = this;

        ivLogo = (ImageView) findViewById(R.id.ivLogonew);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ABOUT US");


        clickMeToViewUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://wfdsa.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ivBack = (ImageView)findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivLogo);
        loadData();
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

    void loadData() {

        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle("Loading");
        dialog.setMessage("Wait...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, GlobalClass.base_url + "wfdsa/apis/Announcement/Record",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject job = new JSONObject(response);
                            JSONObject result = job.getJSONObject("result");
                            String res = result.getString("response");


                            if (res.equals("About Us Data Successfully Recieved")) {

                                JSONObject data = result.getJSONArray("data").getJSONObject(0);


                                Glide.with(activity)
                                        .load(data.getString("upload_pic"))
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .into(ivLogo);

                                contact_number.setText(data.getString("phone"));
                                String about_data = data.getString("about_text");
                                Log.e("about", about_data);
                                about_text.setText(about_data);
                                email.setText(data.getString("contact_email"));
                                address.setText(data.getString("contact_address"));

                                dialog.dismiss();


                            } else {
                                dialog.dismiss();

                                // finish();
                            }
                        } catch (JSONException e) {
                            Log.e("ErrorMessage", e.getMessage());
                            e.printStackTrace();
                            dialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley_error", error.getMessage());
                        // parseVolleyError(error);
                        dialog.dismiss();
                    }
                });

        Volley.newRequestQueue(activity).add(request);


    }
}
