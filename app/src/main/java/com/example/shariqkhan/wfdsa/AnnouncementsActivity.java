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
    public static String URL = GlobalClass.base_url + "wfdsa/apis/Announcement/Announcement";
    @BindView(R.id.rvAnnouncements)
    RecyclerView rvAnnouncements;
    AnnouncementsRVAdapter announcementsRVAdapter;
    ArrayList<AnnouncementsModel> arrayList = new ArrayList<>();
    ArrayList<AnnouncementsModel> arrayListToShow = new ArrayList<>();

    ProgressDialog progressDialog;

    ImageView image;
    int item = 0;
    int total_announcement;
    int total_pages;
    int page=0;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
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
//        LinearLayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvAnnouncements.setLayoutManager(mAnnouncementLayoutManager);
//          rvAnnouncements.setItemAnimator(new DefaultItemAnimator());


//        scrollListener = new EndlessRecyclerViewScrollListener(mAnnouncementLayoutManager) {
//            @Override
//            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                arrayListToShow.add(null);
//                announcementsRVAdapter.notifyItemInserted(arrayListToShow.size());
//
//                Handler mHand = new Handler();
//                mHand.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        //progressBar.setVisibility(View.GONE);
//                        arrayListToShow.remove(arrayListToShow.size() - 1);
//                        announcementsRVAdapter.notifyItemRemoved(arrayListToShow.size());
//                        Log.e("page", page + "");
//                        Log.e("total_announcement", total_announcement + "");
//                        Log.e("total_pages", total_pages + "");
//                        loadNextDataFromApi(page);
//                    }
//                }, 2000);
//
//            }
//        };
//
//
//        rvAnnouncements.addOnScrollListener(scrollListener);
      rvAnnouncements.setAdapter(announcementsRVAdapter);

        announcementsRVAdapter.setOnLoadMoreListener(new AnnouncementsRVAdapter.OnLoadMoreListener() {
                                                         @Override

                                                         public void onLoadMore() {
                                                             arrayListToShow.add(null);
                                                             announcementsRVAdapter.notifyItemInserted(arrayListToShow.size());
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //progressBar.setVisibility(View.GONE);
                        arrayListToShow.remove(arrayListToShow.size() - 1);
                        announcementsRVAdapter.notifyItemRemoved(arrayListToShow.size());
                        Log.e("page", page + "");
                        Log.e("total_announcement", total_announcement + "");
                        Log.e("total_pages", total_pages + "");
                        loadNextDataFromApi();

                    }
                }, 5000);
                                                         }
                                                     }
        );


    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi() {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
        //progressBar.setVisibility(View.VISIBLE);




        if (page <= total_pages) {
            int remaining = total_announcement - item;
            int added_now = 0;

            if (remaining < 6) {
                for (int i = 0; i < remaining; i++) {
                    arrayListToShow.add(arrayList.get(item + i));
                }
                added_now = remaining;

            } else {
                for (int i = 0; i < 6; i++) {
                    arrayListToShow.add(arrayList.get(item + i));
                }
                added_now = 6;

            }

            item += added_now;
            announcementsRVAdapter.notifyDataSetChanged();
            page++;
            announcementsRVAdapter.setLoaded();
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
                    total_announcement = arrayList.size();

                    if (total_announcement % 6 == 0) {
                        total_pages = total_announcement / 6;
                    } else {
                        total_pages = (total_announcement / 6) + 1;
                    }
                    if (total_announcement > 0) {

                        Collections.reverse(arrayList);
                    }
                    if (total_announcement <= 6) {
                        arrayListToShow.addAll(arrayList);
                        item = total_announcement;
                    } else {
                        for (int k = 0; k < 6; k++) {
                            arrayListToShow.add(arrayList.get(k));

                        }
                        item = 6;
                    }
                   // page=1;
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

//    public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
//        RecyclerView.LayoutManager mLayoutManager;
//        // The minimum amount of items to have below your current scroll position
//        // before loading more.
//        private int visibleThreshold = 6;
//        // The current offset index of data you have loaded
//        private int currentPage = 0;
//        // The total number of items in the dataset after the last load
//        private int previousTotalItemCount = 0;
//        // True if we are still waiting for the last set of data to load.
//        private boolean loading = true;
//        // Sets the starting page index
//        private int startingPageIndex = 0;
//
//        public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
//            this.mLayoutManager = layoutManager;
//        }
//
//
//        public int getLastVisibleItem(int[] lastVisibleItemPositions) {
//            int maxSize = 0;
//            for (int i = 0; i < lastVisibleItemPositions.length; i++) {
//                if (i == 0) {
//                    maxSize = lastVisibleItemPositions[i];
//                } else if (lastVisibleItemPositions[i] > maxSize) {
//                    maxSize = lastVisibleItemPositions[i];
//                }
//            }
//            return maxSize;
//        }
//
//        // This happens many times a second during a scroll, so be wary of the code you place here.
//        // We are given a few useful parameters to help us work out if we need to load some more data,
//        // but first we check if we are waiting for the previous load to finish.
//        @Override
//        public void onScrolled(RecyclerView view, int dx, int dy) {
//            int lastVisibleItemPosition = 0;
//            int totalItemCount = mLayoutManager.getItemCount();
//
//            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
//
//
//            // If the total item count is zero and the previous isn't, assume the
//            // list is invalidated and should be reset back to initial state
//            if (totalItemCount < previousTotalItemCount) {
//                this.currentPage = this.startingPageIndex;
//                this.previousTotalItemCount = totalItemCount;
//                if (totalItemCount == 0) {
//                    this.loading = true;
//                }
//            }
//            // If it’s still loading, we check to see if the dataset count has
//            // changed, if so we conclude it has finished loading and update the current page
//            // number and total item count.
//            if (loading && (totalItemCount > previousTotalItemCount)) {
//                loading = false;
//                previousTotalItemCount = totalItemCount;
//            }
//
//            // If it isn’t currently loading, we check to see if we have breached
//            // the visibleThreshold and need to reload more data.
//            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
//            // threshold should reflect how many total columns there are too
//            if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
//                currentPage++;
//                onLoadMore(currentPage, totalItemCount, view);
//                loading = true;
//            }
//        }
//
//        // Call this method whenever performing new searches
//        public void resetState() {
//            this.currentPage = this.startingPageIndex;
//            this.previousTotalItemCount = 0;
//            this.loading = true;
//        }
//
//        // Defines the process for actually loading more data based on page
//        public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
//
//    }

}