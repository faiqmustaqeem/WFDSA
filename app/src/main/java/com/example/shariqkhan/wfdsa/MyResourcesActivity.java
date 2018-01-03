package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.ResourcesRVadapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.custom.ResourcesGroup;
import com.example.shariqkhan.wfdsa.custom.ResourcesSubItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyResourcesActivity extends AppCompatActivity {
    ImageView ivBack;

    @BindView(R.id.rvResources)
    RecyclerView rvResources;
    ProgressDialog dialog;
    List<ResourcesGroup> resourcesGroupList;

    String BASE_URL = "http://codiansoft.com/wfdsa/apis/Resources/Get_resource_file?";

    String resourceId;

    public ResourcesRVadapter adapter;
    List<ResourcesSubItems> resourcesSubItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);
        ButterKnife.bind(this);

        resourceId = getIntent().getStringExtra("RoleName");

        BASE_URL = BASE_URL + "resources_id=" + resourceId;

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Sub Resources");


        Task task = new Task();
        task.execute();


        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = rvResources.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
//        resourcesSubItemsList.add(new ResourcesSubItems("1", "Media Guide"));
//        resourcesSubItemsList.add(new ResourcesSubItems("2", "Global Statistics"));
//        resourcesSubItemsList.add(new ResourcesSubItems("3", "Industry Message Guidebook"));


    }

    public List<ResourcesGroup> getAllResources() {
        return Arrays.asList(
                new ResourcesGroup("1", "Advocacy", resourcesSubItemsList),
                new ResourcesGroup("2", "Association Service", resourcesSubItemsList),
                new ResourcesGroup("3", "Global Regulatory", resourcesSubItemsList)
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            //  String url = "http://codiansoft.com/wfdsa/apis/Announcement/Announcement";

            Log.e("url", BASE_URL);

            String response = getHttpData.getData(BASE_URL);

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

                    resourcesGroupList = new ArrayList<>(resourcesData.length());


                    //   roleArray = new String[rolesArray.length()];
                    // String idArray[] = new String[rolesArray.length()];

                    for (int i = 0; i < resourcesData.length(); i++) {
                        JSONObject job = resourcesData.getJSONObject(i);

                       // String id = job.getString("id");
                        String title = job.getString("Schemes");
                        JSONArray FilesArray = job.getJSONArray("subscheme");
                        resourcesSubItemsList = new ArrayList<>(FilesArray.length());
                        for (int j = 0; j < FilesArray.length(); j++) {
                            JSONObject fileObj = FilesArray.getJSONObject(j);
                            String file_id = fileObj.getString("file_name");
                            String path = fileObj.getString("file");

                            resourcesSubItemsList.add(new ResourcesSubItems(file_id, path));
                        }
                        resourcesGroupList.add(new ResourcesGroup(String.valueOf(i), title, resourcesSubItemsList));

                    }

                    adapter = new ResourcesRVadapter(MyResourcesActivity.this, resourcesGroupList);
                    rvResources.setLayoutManager(new LinearLayoutManager(MyResourcesActivity.this));
                    rvResources.setAdapter(adapter);
                    dialog.dismiss();
                }


//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                dialog.dismiss();
            }

            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MyResourcesActivity.this);
            dialog.setTitle("Fetching Resources");
            dialog.setMessage("Please Wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
    }


}
