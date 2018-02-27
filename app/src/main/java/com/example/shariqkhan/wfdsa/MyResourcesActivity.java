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
import android.widget.Toast;


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

import static com.example.shariqkhan.wfdsa.GlobalClass.selected_resource;

public class MyResourcesActivity extends AppCompatActivity {
    public ResourcesRVadapter adapter;
    ImageView ivBack;
    @BindView(R.id.rvResources)
    RecyclerView rvResources;
    ProgressDialog dialog;
    List<ResourcesGroup> resourcesGroupList;
    String BASE_URL = GlobalClass.base_url + "wfdsa/apis/Resources/Get_resource_by_ctg?";
    String resourceId;
    String parentName;
    List<ResourcesSubItems> resourcesSubItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);
        ButterKnife.bind(this);

        resourceId = getIntent().getStringExtra("category_id");
        parentName = getIntent().getStringExtra("parent_name");

        BASE_URL = BASE_URL + "category_id=" + resourceId;

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(GlobalClass.selected_resource);


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


            Log.e("url", BASE_URL);

            String response = getHttpData.getData(BASE_URL);

            return response;
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
                        JSONArray resourcesData = resultObj.getJSONArray("data");

                        resourcesGroupList = new ArrayList<>();

                        for (int i = 0; i < resourcesData.length(); i++) {

                            JSONObject obj = resourcesData.getJSONObject(i);
//                        String title = obj.getString("title_2");
//                        String path = obj.getString("upload_file");
//                        String file_id = obj.getString("resources_id");
                            String name = obj.getString("name");
                            JSONArray resources_array = obj.getJSONArray("resources");


                            resourcesSubItemsList = new ArrayList<>();

                            for (int index = 0; index < resources_array.length(); index++) {

                                JSONObject resource = resources_array.getJSONObject(index);

                                String title = resource.getString("title_2");
                                String path = resource.getString("upload_file");
                                String file_id = resource.getString("resources_id");
                                String resource_member = resource.getString("resource_member");


                                if (MainActivity.DECIDER.equals("member")) {

                                    Boolean conditionSatisfied = false;
                                    if (GlobalClass.member_role.contains(",")) {
                                        List<String> splitted_roles = Arrays.asList(GlobalClass.member_role.split(","));

                                        if (splitted_roles != null) {
                                            for (int j = 0; j < splitted_roles.size(); j++) {
                                                if (resource_member.contains(splitted_roles.get(j).toString())) {
                                                    conditionSatisfied = true;
                                                    break;
                                                }
                                            }
                                        }

                                    }

                                    if (conditionSatisfied == true) // multiple roles
                                    {
                                        resourcesSubItemsList.add(new ResourcesSubItems(title, path));

                                    } else if (resource_member.contains(GlobalClass.member_role)) {
                                        resourcesSubItemsList.add(new ResourcesSubItems(title, path));


                                    } else if (resource_member.equals("Public")) {
                                        resourcesSubItemsList.add(new ResourcesSubItems(title, path));

                                    } else {
                                        // dont add
                                    }
                                } else {
                                    if (resource_member.equals("Public")) {
                                        resourcesSubItemsList.add(new ResourcesSubItems(title, path));

                                    } else {
                                        // dont add
                                    }


                                }

                            }

                            resourcesGroupList.add(new ResourcesGroup(String.valueOf(i), name, resourcesSubItemsList));


                        }

                        if( resourcesGroupList.size()>0) {
                            adapter = new ResourcesRVadapter(MyResourcesActivity.this, resourcesGroupList);
                            rvResources.setLayoutManager(new LinearLayoutManager(MyResourcesActivity.this));
                            rvResources.setAdapter(adapter);
                        }
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

            }
            else {
                Toast.makeText(MyResourcesActivity.this, "You are not connected to internet !",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }


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
