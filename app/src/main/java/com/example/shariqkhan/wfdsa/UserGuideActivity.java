package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.example.shariqkhan.wfdsa.Adapter.UserGuideAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.Model.TryModel;
import com.example.shariqkhan.wfdsa.Model.UserGuideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class UserGuideActivity extends AppCompatActivity {


    public RecyclerView listOfMembers;
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog ;
    ArrayList<UserGuideModel> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);
        layoutManager = new LinearLayoutManager(this);
        listOfMembers.setLayoutManager(layoutManager);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new UserGuideAdapter(arrayList, this);
        listOfMembers.setAdapter(adapter);

        Task task = new Task();
        task.execute();


    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = "http://codiansoft.com/wfdsa/apis/UserGuide/guide";

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
                    //    roleArray = new String[rolesArray.length()];


                    for (int i = 0; i < rolesArray.length(); i++) {
                        UserGuideModel model = new UserGuideModel();
                        JSONObject obj = rolesArray.getJSONObject(i);

                        model.setVideotitle(obj.getString("title"));
                        model.setUploadVideo(obj.getString("upload_video"));
                        arrayList.add(model);
                        adapter.notifyDataSetChanged();
                    }
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
            progressDialog = new ProgressDialog(UserGuideActivity.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

}
