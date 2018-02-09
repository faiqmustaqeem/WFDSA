package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class LeaderShipActivity extends AppCompatActivity {
    public static String URL = GlobalClass.base_url + "wfdsa/apis/Member/Leadership";
    public ListView listOfMembers;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    String roleArray[];
    String Array[] = {"Ceo Council", "Operational Group", "Board of Delegates", "Association Advisory Council"};
    String idArray[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership);

        listOfMembers = (ListView) findViewById(R.id.memberList);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Task task = new Task();
        task.execute();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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
                    roleArray = new String[rolesArray.length()];
                    idArray = new String[rolesArray.length()];

                    for (int i = 0; i < rolesArray.length(); i++) {
                        LeaderShipModel model = new LeaderShipModel();
                        JSONObject obj = rolesArray.getJSONObject(i);

                        model.setGetRoleid(obj.getString("member_role_id"));
                        model.setGetRoleName(obj.getString("name"));
                        roleArray[i] = model.getGetRoleName();
                        idArray[i] = model.getGetRoleid();

                    }
                }
                if(roleArray.length > 0) {
                    listOfMembers.setAdapter(new ArrayAdapter<String>(LeaderShipActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, roleArray));
                    listOfMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String get = parent.getItemAtPosition(position).toString();
                            String roleId = idArray[position];
                            String roleName = roleArray[position];
                            Log.e("role", get);
                            Log.e("id", roleId);
//                if (get.equals(Array[0])) {
//                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
//                } else if (get.equals(Array[1])) {
//                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
//                } else if (get.equals(Array[2])) {
//                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
//                } else if (get.equals(Array[3])) {
//                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
//
//                }

                            Intent intent = new Intent(LeaderShipActivity.this, CEOActivity.class);
                            intent.putExtra("RoleName", roleId);
                            intent.putExtra("Name", roleName);
                            intent.putExtra("url", GlobalClass.base_url + "wfdsa/roster/get_role_members?");

                            startActivity(intent);

                        }
                    });
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
            progressDialog = new ProgressDialog(LeaderShipActivity.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}