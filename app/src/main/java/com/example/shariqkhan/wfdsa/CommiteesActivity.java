package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;
import com.example.shariqkhan.wfdsa.Model.ModelMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class CommiteesActivity extends AppCompatActivity {

    public ListView listOfMembers;
    Toolbar toolbar;
    private String[] roleArray;
    private String[] idArray;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        listOfMembers = (ListView) findViewById(R.id.memberList);


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

        //  final String Array[] = {"Advocacy", "Association Services", "Ethics", "Global Regularity", "Governance and Finance"};

        Task task = new Task();
        task.execute();

    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = "http://codiansoft.com/wfdsa/apis/Member/Committee";

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
                listOfMembers.setAdapter(new ArrayAdapter<String>(CommiteesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, roleArray));
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

                        Intent intent = new Intent(CommiteesActivity.this, CEOActivity.class);
                        intent.putExtra("RoleName", roleId);
                        intent.putExtra("Name", roleName);

                        startActivity(intent);

                    }
                });


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
            progressDialog = new ProgressDialog(CommiteesActivity.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}
