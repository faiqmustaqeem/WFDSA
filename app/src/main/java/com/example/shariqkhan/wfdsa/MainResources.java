package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

                        arrayList.add(model);
//
//                        filesArray[i] = job.getString("title_2");
//                        idArray[i] = job.getString("resources_id");
//                        typeArray[i] = job.getString("resource_member");
                        //         titlesArray[i] = job.getString("title");

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
