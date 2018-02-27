package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shariqkhan.wfdsa.Adapter.CEOAdapter;
import com.example.shariqkhan.wfdsa.Adapter.MemberAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MemberActivity extends AppCompatActivity {

    public RecyclerView listOfMembers;
    MemberAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMember> arrayList = new ArrayList<>();
    ArrayList<ModelMember> arrayListSave;
    public static String filterableString = "";
    final ArrayList<String> countries = new ArrayList<String>();
    ProgressDialog dialog;


    Toolbar toolbar;
    ImageView imageFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        countries.add("All");

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);

        layoutManager = new LinearLayoutManager(this);
        listOfMembers.setLayoutManager(layoutManager);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Task2 task = new Task2();
        task.execute();

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageFilter = (ImageView) toolbar.findViewById(R.id.filter);


        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> items = new ArrayList<String>(countries);
                new MaterialDialog.Builder(MemberActivity.this)
                        .title("Select Region")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                filterableString = text.toString();
                                    adapter.getFilter().filter(filterableString);

                                return true;

                            }
                        })
                        .positiveText("Choose")
                        .show();


            }
        });


    }

    private class Task2 extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url+"wfdsa/apis/member/DSA_member";

            Log.e("url", url);

            String response = getHttpData.getData(url);

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
                        Log.e("SuccessFull", "Inside");
                        JSONArray rolesArray = resultObj.getJSONArray("data");
                        //   roleArray = new String[rolesArray.length()];
                        // String idArray[] = new String[rolesArray.length()];

                        for (int i = 0; i < rolesArray.length(); i++) {
                            ModelMember model = new ModelMember();
                            JSONObject obj = rolesArray.getJSONObject(i);

                            model.setMemberPhone(obj.getString("phone"));
                            model.setMemberEmail(obj.getString("email"));
                            model.setMemberFax(obj.getString("fax"));
                            // countries.add(obj.getString("country"));
                            model.setCountry(obj.getString("country"));
                            model.setMemberName(obj.getString("member_name"));
                            model.setMemberWeb(obj.getString("website"));


                            model.setCompany_logo(obj.getString("company_logo"));
                            model.setFlag_pic(obj.getString("flag_pic"));
                            model.setCompanyAddress(obj.getString("address"));
                            model.setCompanyName(obj.getString("company_name"));
                            model.setRegion(obj.getString("region"));

                            countries.add(obj.getString("region"));

                            if(obj.getString("status").equals("1"))
                            {
                                arrayList.add(model);
                            }

                        }
                        arrayListSave = new ArrayList<>(arrayList);
                        Log.e("Clone", String.valueOf(arrayListSave.size()));


                        adapter = new MemberAdapter(arrayList, MemberActivity.this);
                        listOfMembers.setAdapter(adapter);

                    }
                    dialog.dismiss();



                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    dialog.dismiss();
                    e.printStackTrace();

                }


            }
            else {
                Toast.makeText(MemberActivity.this , "you are not connected to internet !" , Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MemberActivity.this);
            dialog.setTitle("Fetching Information");
            dialog.setMessage("Please Wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


        }
    }
}
