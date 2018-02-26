package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;

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
    public static String URL = GlobalClass.base_url + "wfdsa/apis/Announcement/Announcement_byrole";
    public static String URL_LOAD_MORE = GlobalClass.base_url + "wfdsa/apis/Announcement/Announcement_byrole_loading";

    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    AnnouncementsRVAdapter announcementsRVAdapter;
    ArrayList<AnnouncementsModel> arrayList = new ArrayList<>();
    ArrayList<AnnouncementsModel> arrayListToShow = new ArrayList<>();

    ProgressDialog progressDialog;
    String last_announcement_id="";

    ImageView image;
    int item = 0;
    int total_announcement;
    int total_pages;
    int page=0;

 //   private EndlessRecyclerViewScrollListener scrollListener;

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


        Task task = new Task();
        task.execute();

        announcementsRVAdapter = new AnnouncementsRVAdapter(this, arrayListToShow,rvAnnouncements);

      rvAnnouncements.setAdapter(announcementsRVAdapter);

        announcementsRVAdapter.setOnLoadMoreListener(new AnnouncementsRVAdapter.OnLoadMoreListener() {
                                                         @Override

                                                         public void onLoadMore() {
                                                             Log.e("page", page + "");
                                                                         Log.e("total_members", total_announcement + "");
                                                                         Log.e("total_pages", total_pages + "");
                                                             if (page < total_pages) {
                                                                 arrayListToShow.add(null);
                                                                 announcementsRVAdapter.notifyItemInserted(arrayListToShow.size());
                                                                 loadNextDataFromApi();

//
//                                                                         arrayListToShow.remove(arrayListToShow.size() - 1);
//                                                                         announcementsRVAdapter.notifyItemRemoved(arrayListToShow.size());
//                                                                         Log.e("page", page + "");
//                                                                         Log.e("total_members", total_announcement + "");
//                                                                         Log.e("total_pages", total_pages + "");
//
                                                             }
                                                         }
                                                     }
        );


    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi() {





        if (page < total_pages) {

            TaskLoadMore task=new TaskLoadMore();
            task.execute();
            //announcementsRVAdapter.notifyItemRangeInserted(oldSize,added_now);

        }


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

            String url = URL + "?signin_type=" + MainActivity.SIGNIN_TYPE + "&role=" + MainActivity.ROLE;
            url = url.replace(" ", "%20");

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

                        //  String announce_for = obj.getString("announce_for");

                        arrayList.add(model);

                        if(i==rolesArray.length()-1)
                        {
                            last_announcement_id=model.getId();
                        }

                    }
                    total_announcement = resultObj.getInt("total_counts");
                    Log.e("total_announcement",total_announcement+"");

                    if (total_announcement % 6 == 0) {
                        total_pages = total_announcement / 6;
                    } else {
                        total_pages = (total_announcement / 6) + 1;
                    }
//                    if (total_announcement > 0) {
//
//                        // Collections.reverse(arrayList);
//                    }
                    if (total_announcement <= 6) {
                        arrayListToShow.addAll(arrayList);
                        item = total_announcement;
                    } else {
                        for (int k = 0; k < 6; k++) {
                            arrayListToShow.add(arrayList.get(k));

                        }
                        item = 6;
                    }
                    page = 1;
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

    private class TaskLoadMore extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL_LOAD_MORE + "?signin_type=" + MainActivity.SIGNIN_TYPE +"&announcement_id="+last_announcement_id +"&role=" + MainActivity.ROLE;
            url = url.replace(" ", "%20");

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

                    arrayListToShow.remove(arrayListToShow.size() - 1);
                    announcementsRVAdapter.notifyItemRemoved(arrayListToShow.size());

                    JSONArray rolesArray = resultObj.getJSONArray("data");
                    //   roleArray = new String[rolesArray.length()];
                    // String idArray[] = new String[rolesArray.length()];

                    int arrayListOldIndex=arrayListToShow.size();
                        arrayList.clear();
                    for (int i = 0; i < rolesArray.length(); i++) {
                        AnnouncementsModel model = new AnnouncementsModel();
                        JSONObject obj = rolesArray.getJSONObject(i);
                        model.setId(obj.getString("announcement_id"));
                        model.setTitle(obj.getString("title"));
                        model.setDescription(obj.getString("announcement_message"));
                        model.setImage(obj.getString("upload_image"));
                        model.setDate(obj.getString("date"));

                        arrayList.add(model);

                        if(i==rolesArray.length()-1)
                        {
                            last_announcement_id=model.getId();
                        }



                    }



                    arrayListToShow.addAll(arrayList);
                    announcementsRVAdapter.notifyItemRangeInserted(arrayListOldIndex,rolesArray.length());

                    page++;
                    announcementsRVAdapter.setLoaded();

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



}