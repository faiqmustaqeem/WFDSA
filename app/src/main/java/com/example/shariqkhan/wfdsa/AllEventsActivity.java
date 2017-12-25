package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shariqkhan.wfdsa.Adapter.EventsRVAdapter;
import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.example.shariqkhan.wfdsa.custom.RecyclerTouchListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEventsActivity extends AppCompatActivity {

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;
    EventsRVAdapter eventsRVAdapter;
    ImageView image;
    public ArrayList<EventsModel> arrayList = new ArrayList<>();
    public ProgressDialog progressDialog;

    String URL = "http://codiansoft.com/wfdsa/apis/Event/Events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        ButterKnife.bind(this);

//        RequestQueue queue = Volley.newRequestQueue(this);
//
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
//                new Response.Listener<JSONObject>()
//                {
//
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.getMessage());
//                    }
//                }
//        );
//
//        queue.add(getRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("MY EVENTS");
        image = (ImageView) findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eventsRVAdapter = new EventsRVAdapter(this, arrayList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvEvents.setLayoutManager(mAnnouncementLayoutManager);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvEvents.setAdapter(eventsRVAdapter);

        Task task = new Task();
        task.execute();

        rvEvents.addOnItemTouchListener(new RecyclerTouchListener(AllEventsActivity.this, rvEvents, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EventsModel eventsModel = arrayList.get(position);


                if (!MainActivity.DECIDER.equals("member")) {
                    if (eventsModel.getPersonal().equals("member")) {
                        Toast.makeText(AllEventsActivity.this, "Members Access Only!", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent i = new Intent(AllEventsActivity.this, SelectedEventActivity.class);
                        i.putExtra("eventid", eventsModel.getId());
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent(AllEventsActivity.this, SelectedEventActivity.class);
                    i.putExtra("eventid", eventsModel.getId());
                    startActivity(i);
                }

                //     Toast.makeText(AllEventsActivity.this, eventsModel.getId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    private class Task extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AllEventsActivity.this);
            progressDialog.setTitle("Fetching Events");

            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String[] params) {

            String getResponse = getJson();
            stream = getResponse;

            return stream;

        }

        private String getJson() {
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost post = new HttpPost(URL);
            Log.e("Must", "Must");
//
//
//            List<NameValuePair> parameters = new ArrayList<>();
//            parameters.add(new BasicNameValuePair("first_name", firstName));
//            parameters.add(new BasicNameValuePair("last_name", lastName));
//            parameters.add(new BasicNameValuePair("email", email));
//            parameters.add(new BasicNameValuePair("country", getItem));
//            parameters.add(new BasicNameValuePair("contact", contactNum));
//            parameters.add(new BasicNameValuePair("password", password));
//            parameters.add(new BasicNameValuePair("confirm_password", confirmPassword));
//
//            Log.e("f", firstName);
//            Log.e("l", lastName);
//            Log.e("e", email);
//            Log.e("c", getItem);
//            Log.e("c", contactNum);
//            Log.e("p", password);
//            Log.e("c", confirmPassword);

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
//                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
//                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    Log.e("reader", Line);
                    Log.e("buffer", buffer.toString());
                    buffer.append(Line);

                }
                reader.close();

            } catch (Exception o) {
                Log.e("Error", o.getMessage());

            }
            return buffer.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jsonobj;
            if (s != null) {
                try {
                    jsonobj = new JSONObject(s);
                    Log.e("JSON", s);


                    JSONObject result = jsonobj.getJSONObject("result");
                    String checkResult = result.getString("status");

                    if (checkResult.equals("success")) {


                        JSONArray data = result.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            EventsModel model = new EventsModel();
                            JSONObject job = data.getJSONObject(i);
                            model.setId(job.getString("event_id"));
                            model.setEventTitle(job.getString("title"));
                            model.setVenueCity(job.getString("place"));
                            String sub = job.getString("start_date");

                            String filter = sub.substring(8, 10);

                            model.setDay(filter);

                            model.setMonth(job.getString("start_date").substring(5, 7));
                            model.setYear(job.getString("start_date").substring(0, 4));
                            model.setTime(job.getString("start_date").substring(11, 19));
                            //   model.setImageUrl(job.getString("upload_image"));
                            model.setPersonal(job.getString("permission"));


                            arrayList.add(model);
                        }
                        eventsRVAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(AllEventsActivity.this, "Error!!", Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage", e.getMessage());

                    progressDialog.dismiss();
                }


            }

            progressDialog.dismiss();
        }
    }


    private String getJson() {
        HttpClient httpClient = new DefaultHttpClient();
        String urltoSend = "";

        HttpPost post = new HttpPost(urltoSend);

        StringBuilder buffer = new StringBuilder();

        List<NameValuePair> pairs = new ArrayList<>();

        pairs.add(new BasicNameValuePair("", ""));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs);
            post.setEntity(encodedFormEntity);
            HttpResponse res = httpClient.execute(post);

            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));
            String Line = "";
            while ((Line = reader.readLine()) != null) {
                buffer.append(Line);

            }

            reader.close();

        } catch (UnsupportedEncodingException e) {
            Log.e("EncodingException", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("ClientException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }


        return buffer.toString();
    }


}