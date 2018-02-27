package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.CEOAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.ModelMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Codiansoft on 12/6/2017.
 */

public class CEOActivity extends AppCompatActivity {


    public RecyclerView listOfMembers;
    CEOAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMember> arrayList = new ArrayList<>();
    ArrayList<ModelMember> arrayListToShow = new ArrayList<>();

    String sendRole;
    ProgressDialog dialog;
    String roleName;


    TextView txt;

    String URL = "";


    Toolbar toolbar;
    int item = 0;
    int total_members;
    int total_pages;
    int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceo_activity);
        sendRole = getIntent().getStringExtra("RoleName");


        roleName = getIntent().getStringExtra("Name");

        URL = getIntent().getStringExtra("url");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt = (TextView) toolbar.findViewById(R.id.tollbarText);
        txt.setText(roleName);

        roleName = roleName.replace(" ", "%20");
        URL = URL + "role_id=" + roleName;
        Log.e("url_new" , URL);

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);

        //layoutManager = new LinearLayoutManager(this);
        //listOfMembers.setLayoutManager(layoutManager);




        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Task2 task = new Task2();
        task.execute();

        adapter = new CEOAdapter(arrayListToShow, CEOActivity.this, listOfMembers);
        listOfMembers.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new CEOAdapter.OnLoadMoreListener() {
                                          @Override

                                          public void onLoadMore() {

                                              adapter.notifyItemInserted(arrayListToShow.size());
                                              if (page <= total_pages) {
                                                  arrayListToShow.add(null);
                                                  Handler mHand = new Handler();
                                                  mHand.postDelayed(new Runnable() {

                                                      @Override
                                                      public void run() {
                                                          // TODO Auto-generated method stub
                                                          //progressBar.setVisibility(View.GONE);
                                                          arrayListToShow.remove(arrayListToShow.size() - 1);
                                                          adapter.notifyItemRemoved(arrayListToShow.size());
                                                          Log.e("page", page + "");
                                                          Log.e("total_members", total_members + "");
                                                          Log.e("total_pages", total_pages + "");
                                                          loadNextDataFromApi();

                                                      }
                                                  }, 1000);
                                              }
                                          }
                                      }
        );

    }

    public void loadNextDataFromApi() {



        if (page <= total_pages) {
            int remaining = total_members - item;
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
            adapter.notifyDataSetChanged();
            page++;
            adapter.setLoaded();
        }


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
            if(s!=null)
            {
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
                        total_members = arrayList.size();

                        if (total_members % 6 == 0) {
                            total_pages = total_members / 6;
                        } else {
                            total_pages = (total_members / 6) + 1;
                        }
                        if (total_members > 0) {

                            // Collections.reverse(arrayList);
                        }
                        if (total_members <= 6) {
                            arrayListToShow.addAll(arrayList);
                            item = total_members;
                        } else {
                            for (int k = 0; k < 6; k++) {
                                arrayListToShow.add(arrayList.get(k));

                            }
                            item = 6;
                        }
                        page = 1;
                        adapter.notifyDataSetChanged();

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
            else {
                Toast.makeText(CEOActivity.this , "You are not connected to internet" , Toast.LENGTH_LONG).show();
                dialog.dismiss();
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
