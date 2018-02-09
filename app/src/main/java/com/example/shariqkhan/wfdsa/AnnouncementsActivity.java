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
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnnouncementsActivity extends AppCompatActivity {
    public static String URL = GlobalClass.base_url + "wfdsa/apis/Announcement/Announcement";
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    AnnouncementsRVAdapter announcementsRVAdapter;
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
        //  rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
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

                        String announce_for = obj.getString("announce_for");
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
                                arrayList.add(model);
                            } else if (announce_for.contains(GlobalClass.member_role)) {
                                arrayList.add(model);

                            } else if (announce_for.equals("Public")) {
                                arrayList.add(model);
                            } else {
                                // dont add
                            }
                        } else {
                            if (announce_for.equals("Public")) {
                                arrayList.add(model);
                            } else {
                                // dont add
                            }


                        }


                    }
                    if(arrayList.size()>0) {
                        Collections.reverse(arrayList);
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