package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.Adapter.AnnouncementsRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.CEOAdapter;
import com.example.shariqkhan.wfdsa.Adapter.MemberAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.ModelMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/6/2017.
 */

public class CEOActivity extends AppCompatActivity {


    public RecyclerView listOfMembers;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMember> arrayList = new ArrayList<>();
    String sendRole;
    ProgressDialog dialog;
    String roleName;


    TextView txt;

    String URL = "";


    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceo_activity);
        sendRole = getIntent().getStringExtra("RoleName");


        roleName = getIntent().getStringExtra("Name");

        URL = getIntent().getStringExtra("url");


        roleName = roleName.replace(' ', '_');
        URL = URL + "role_id=" + roleName;
        Log.e("url_new" , URL);

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);

        layoutManager = new LinearLayoutManager(this);
        listOfMembers.setLayoutManager(layoutManager);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt = (TextView) toolbar.findViewById(R.id.tollbarText);
        txt.setText(roleName+" Members");

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        for (int i = 0; i < 12; i++) {
//
//            ModelMember member = new ModelMember();
//            member.setCompanyAddress("bbshoppingmall-secondfloor-office3");
//            member.setMemberEmail("codianSoft@org");
//            member.setMemberName("Shariq Khan");
//            member.setMemberFax("03342477874");
//            member.setCompanyName("Codian-Soft-");
//            member.setMemberPhone("03342477874");
//            member.setMemberWeb("www.codiansoft.com");
//            member.setCountry("Argentina");
//            arrayList.add(member);
//
//        }

        Task2 task = new Task2();
        task.execute();
    }

    private class Task2 extends AsyncTask<Object, Object, String> {

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
            Log.e("Resources", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                String resultObj = jsonObject.getString("success");
                String getstatus = jsonObject.getString("msg");

                if (getstatus.startsWith("Members List is generating")) {
                    Log.e("SuccessFull", "Inside");
                    Log.e("CEO" , s);

                    JSONArray rolesArray = jsonObject.getJSONArray("record");
                    //   roleArray = new String[rolesArray.length()];
                    // String idArray[] = new String[rolesArray.length()];

                    for (int i = 0; i < rolesArray.length(); i++) {
                        ModelMember model = new ModelMember();
                        JSONObject obj = rolesArray.getJSONObject(i);



                        model.setMemberPhone(obj.getString("telephone"));
                        model.setMemberEmail(obj.getString("email"));
                        model.setMemberFax(obj.getString("fax"));
                        model.setCountry(obj.getString("name"));
                        model.setFirstname(obj.getString("member_name"));
                        model.setLastname("");
                        model.setTitle(obj.getString("title"));
                        model.setDesignation(obj.getString("designation"));
                        model.setWfdsa_title(obj.getString("wfdsa_title"));
                        model.setUpload_image(obj.getString("upload_image"));
                        model.setCompanyAddress(obj.getString("address"));
                        model.setCompanyName(obj.getString("company"));
                        model.setFlag_pic(obj.getString("flag_pic"));

                        arrayList.add(model);

                    }

                    adapter = new CEOAdapter(arrayList, CEOActivity.this);
                    listOfMembers.setAdapter(adapter);

                }
                dialog.dismiss();


//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                dialog.dismiss();
                e.printStackTrace();

            }


        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(CEOActivity.this);
            dialog.setTitle("Fetching Information");
            dialog.setMessage("Please Wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


        }
    }

}
