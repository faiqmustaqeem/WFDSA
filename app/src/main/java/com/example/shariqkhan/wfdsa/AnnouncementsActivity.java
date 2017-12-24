package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnnouncementsActivity extends AppCompatActivity {
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    AnnouncementsRVAdapter announcementsRVAdapter;

    public static String URL = "http://codiansoft.com/wfdsa/apis/Announcement/Announcement";
    ArrayList<AnnouncementsModel> arrayList = new ArrayList<>();
    ProgressDialog progressDialog;

    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ANNOUNCEMENT");
        ImageView image;
        image = (ImageView) findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        announcementsRVAdapter = new AnnouncementsRVAdapter(this, arrayList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
        rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
        rvAnnouncements.setAdapter(announcementsRVAdapter);


        Task task = new Task();


        task.execute();


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

            String url = URL;

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
                        arrayList.add(model);


                    }
                    announcementsRVAdapter.notifyDataSetChanged();

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
            progressDialog = new ProgressDialog(AnnouncementsActivity.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

}