package com.example.shariqkhan.wfdsa;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.MainResourceAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.MainResourceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Codiansoft on 1/2/2018.
 */

public class MainResources extends AppCompatActivity {

    RecyclerView list;
    ProgressDialog dialog;
    String[] filesArray;
    String idArray[];
    String[] typeArray;

    Toolbar toolbar;
    ArrayList<MainResourceModel> arrayList = new ArrayList<>();

    TextView txt;
    BroadcastReceiver onComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resources);
        list = (RecyclerView) findViewById(R.id.recView);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt = (TextView) toolbar.findViewById(R.id.tollbarText);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Task5 task = new Task5();
        task.execute();

        onComplete = new BroadcastReceiver() {

            public void onReceive(Context ctxt, Intent intent) {

                // get the refid from the download manager
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

// remove it from our list
                GlobalClass.list_downloads.remove(referenceId);

// if list is empty means all downloads completed
                if (GlobalClass.list_downloads.isEmpty()) {

// show a notification
                    Log.e("INSIDE", "" + referenceId);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainResources.this)
                                    .setSmallIcon(R.drawable.frontlogo)
                                    .setContentTitle(GlobalClass.download_file_name)
                                    .setContentText("Download completed");


                    PendingIntent contentIntent = PendingIntent.getActivity(MainResources.this, 0,
                            new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(contentIntent);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(455, mBuilder.build());


                }

            }
        };

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);


    }

    private class Task5 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            //String url = "http://codiansoft.com/wfdsa/apis/Announcement/Announcement";

            Log.e("url", GlobalClass.base_url+"wfdsa/apis/Resources/Get_resource");

            String response = getHttpData.getData(GlobalClass.base_url+"wfdsa/apis/Resources/Get_resource");

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

//
//                    filesArray = new String[resourcesData.length()];
//                    idArray = new String[resourcesData.length()];
//
//                    typeArray = new String[resourcesData.length()];
                    //   titlesArray = new String[resourcesData.length()];
                    for (int i = 0; i < resourcesData.length(); i++) {
                        JSONObject job = resourcesData.getJSONObject(i);
                        MainResourceModel model = new MainResourceModel();
                        model.setResource_id(job.getString("resources_id"));
                        model.setTitle(job.getString("title_2"));
                        model.setResource_memeber(job.getString("resource_member"));
                        model.setParent_name(job.getString("parent_category_name"));


                        String resource_member = job.getString("resource_member");
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
                                arrayList.add(model);
                            } else if (resource_member.contains(GlobalClass.member_role)) {
                                arrayList.add(model);

                            } else if (resource_member.equals("Public")) {
                                arrayList.add(model);
                            } else {
                                // dont add
                            }
                        } else {
                            if (resource_member.equals("Public")) {
                                arrayList.add(model);
                            } else {
                                // dont add
                            }


                        }

                    }

                    MainResourceAdapter adapter = new MainResourceAdapter(arrayList, MainResources.this);

                    list.setLayoutManager(new LinearLayoutManager(MainResources.this));
                    list.setAdapter(adapter);


                }

//
//                list.setAdapter(new ArrayAdapter<String>(MainResources.this, android.R.layout.simple_list_item_1, android.R.id.text1, filesArray));
//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String get = parent.getItemAtPosition(position).toString();
//                        String roleId = idArray[position];
//                        String roleName = filesArray[position];
//                        Log.e("role", get);
//                        Log.e("id", roleId);
////                if (get.equals(Array[0])) {
////                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
////                } else if (get.equals(Array[1])) {
////                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
////                } else if (get.equals(Array[2])) {
////                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
////                } else if (get.equals(Array[3])) {
////                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
////
////                }
//
//                        if (typeArray[position].equals("Member") && MainActivity.DECIDER.equals("nonmember")) {
//                            Toast.makeText(MainResources.this, "This resource restricted to members only!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Intent intent = new Intent(MainResources.this, MyResourcesActivity.class);
//                            intent.putExtra("RoleName", roleId);
//                            //  intent.putExtra("Name", roleName);
//
//                            startActivity(intent);
//
//                        }
//
//                    }
//                });

                dialog.dismiss();
//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();

            }
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainResources.this);
            dialog.setTitle("Fetching Resources");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            // PGdialog.show();

        }

    }


}
