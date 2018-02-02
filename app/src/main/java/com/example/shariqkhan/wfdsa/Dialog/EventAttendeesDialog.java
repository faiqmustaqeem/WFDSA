package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shariqkhan.wfdsa.Adapter.AttendeesRVAdapter;
import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.Model.AttendeesModel;
import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.Singleton.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventAttendeesDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ArrayList<AttendeesModel> attendeesList = new ArrayList<AttendeesModel>();
    //
//    @BindView(R.id.tvRegister)
//    TextView tvRegister;
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    @BindView(R.id.rvAttendees)
    RecyclerView rvAttendees;

    @BindView(R.id.tvAttendeesQty)
    TextView tvQty;
    @BindView(R.id.none_going)
    TextView none_going;
    AttendeesRVAdapter attendeesRVAdapter;

    public EventAttendeesDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_attendees_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);


        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUI();
    }

    private void initUI() {
        attendeesList.clear();
        attendeesRVAdapter = new AttendeesRVAdapter(act, attendeesList);

        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvAttendees.setLayoutManager(mAnnouncementLayoutManager);
        rvAttendees.setItemAnimator(new DefaultItemAnimator());
        getMembersGoing();
    }

    public void getMembersGoing() {
        try {
            StringRequest request = new StringRequest(Request.Method.GET, GlobalClass.base_url + "wfdsa/apis/Event/EventMembers?event_id=" + GlobalClass.selelcted_event_id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.e("json", response);
                                JSONObject job = new JSONObject(response);
                                JSONObject result = job.getJSONObject("result");
                                String res = result.getString("response");
                                Log.e("response", res);

                                if (res.startsWith("Members Details Successfully Recieved")) {

                                    JSONArray dataArray = result.getJSONArray("data");

                                    tvQty.setText(String.valueOf(dataArray.length()));
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject data = dataArray.getJSONObject(i);

                                        String first_name = data.getString("first_name");
                                        String last_name = data.getString("last_name");
                                        //   String id=data.getString("member")

                                        attendeesList.add(new AttendeesModel(first_name + " " + last_name));

                                    }

                                    if (attendeesList.size() > 0) {

                                        rvAttendees.setAdapter(attendeesRVAdapter);
                                    } else {
                                        none_going.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    none_going.setVisibility(View.VISIBLE);

                                }
                            } catch (JSONException e) {
                                Log.e("ErrorMessage", e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley_error", error.networkResponse.statusCode + "");
                    none_going.setVisibility(View.VISIBLE);
                }
            });
            AppSingleton.getInstance().addToRequestQueue(request);

        } catch (Exception e) {
            none_going.setVisibility(View.VISIBLE);
            Log.e("Exception", e.getMessage());

        }
        // Volley.newRequestQueue(RegisterEvent.this).add(request);
    }

    @OnClick({R.id.tvCancel})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }
}