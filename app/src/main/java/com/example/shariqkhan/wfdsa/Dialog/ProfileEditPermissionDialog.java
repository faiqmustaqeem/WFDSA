package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.AllEventsActivity;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.example.shariqkhan.wfdsa.ProfileActivity;
import com.example.shariqkhan.wfdsa.R;

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


/**
 * Created by Codiansoft on 11/4/2017.
 */

public class ProfileEditPermissionDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    EditText etPassword;
    TextView tvSubmit, tvCancel;
    String password;
    public static boolean editedOption = false;

    public ProfileEditPermissionDialog(Activity a, String password) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.password = password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_profile_edit_permission);
        etPassword = (EditText) findViewById(R.id.etPassword);
        //   etPassword.setText(password);

        etPassword.setEnabled(true);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvSubmit.setClickable(false);


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etPassword.getText().toString().equals("")) {
                    editedOption = true;
                    tvSubmit.setTextColor(c.getResources().getColor(R.color.colorAccent));
                    tvSubmit.setClickable(true);
                } else {
                    tvSubmit.setTextColor(c.getResources().getColor(android.R.color.darker_gray));
                    tvSubmit.setClickable(false);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:

                Task task = new Task();
                task.execute();

                break;
            case R.id.tvCancel:
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
    }

    private class Task extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(c);
            progressDialog.setTitle("Verifying");

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


            HttpPost post = new HttpPost("");
            Log.e("Must", "Must");
//
//
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("member_id", MainActivity.getId));
            parameters.add(new BasicNameValuePair("password", etPassword.getText().toString()));
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
                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
                post.setEntity(encoded);
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
                        ProfileActivity.canEdit = true;

                    } else {
                        Toast.makeText(c, "Error!!", Toast.LENGTH_LONG).show();


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