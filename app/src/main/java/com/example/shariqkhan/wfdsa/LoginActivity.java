package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.shariqkhan.wfdsa.SplashActivity.fetchAnnouncements;
import static com.example.shariqkhan.wfdsa.SplashActivity.fetchEvents;


public class LoginActivity extends AppCompatActivity {
    public static String BASE_URL = GlobalClass.base_url + "wfdsa/api/login";
    public static String decider = "1";
    @BindView(R.id.ivWFDSALogo)
    ImageView ivWFDSALogo;
    @BindView(R.id.tvMember)
    TextView tvMember;
    @BindView(R.id.tvNonMember)
    TextView tvNonMember;
    @BindView(R.id.clMember)
    ConstraintLayout clMember;
    @BindView(R.id.clNonMember)
    ConstraintLayout clNonMember;
    @BindView(R.id.llMember)
    LinearLayout llMember;
    @BindView(R.id.llNonMember)
    LinearLayout llNonMember;
    @BindView(R.id.tvNonMemberSignUp)
    TextView tvNonMemberSignUp;
    @BindView(R.id.tvMemberForgetPassword)
    TextView tvMemberForgetPassword;
    @BindView(R.id.tvMemberSignIn)
    TextView tvMemberSignIn;
    @BindView(R.id.tvNonMemberSignIn)
    TextView tvNonMemberSignIn;
    @BindView(R.id.etEmail)
            EditText etNonMemberEmail;
    @BindView(R.id.etNonMemberPassword)
    EditText etNonMemberPassowrd;
    TextInputLayout etMemberEmail;
    TextInputLayout etMemberPass;
    ImageView ivBack;
    String getEmail;
    String getPassword;
    android.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
      //  Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivWFDSALogo);

        etMemberEmail = (TextInputLayout) findViewById(R.id.tilMemberEmail);
        etMemberPass = (TextInputLayout) findViewById(R.id.tilMemberPassword);

        tvMemberSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEmail = etMemberEmail.getEditText().getText().toString();
                getPassword = etMemberPass.getEditText().getText().toString();
                Log.e("Email", getEmail);
                Log.e("Password", getPassword);

                SharedPreferences.Editor edit = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                edit.putString("type", "member");
                edit.apply();

                fetchEvents();
                fetchAnnouncements();
                Task task = new Task();
                task.execute();


                //                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//                LoginActivity.this.startActivity(loginIntent);
//                LoginActivity.this.finish();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Sign In");
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvMember:
                decider = "1";
                llMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));

                clMember.setVisibility(View.VISIBLE);
                clNonMember.setVisibility(View.GONE);

                SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                editor.putString("type", "member");
                editor.apply();

                break;

            case R.id.tvNonMember:
                decider = "2";
                llMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clNonMember.setVisibility(View.VISIBLE);
                clMember.setVisibility(View.GONE);

                SharedPreferences.Editor edit = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                edit.putString("type", "nonmember");
                edit.apply();

                break;

            case R.id.tvNonMemberSignUp:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.tvNonMemberSignIn:
                getEmail = etNonMemberEmail.getText().toString();
                getPassword = etNonMemberPassowrd.getText().toString();
                Log.e("Message", "Dhun dhun dhun");

                fetchEvents();
                fetchAnnouncements();
                Task task = new Task();
                task.execute();

                break;

            case R.id.tvMemberForgetPassword:
            case R.id.tvNonMemberForgetPassword:
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(forgotPasswordIntent);
                finish();
                break;
        }
    }

    public void login() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GlobalClass.base_url + "wfdsa/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
//

                        if (s != null) {
                            try {
                                Log.e("login", s);
                                JSONObject jsonobj = new JSONObject(s);
                                Log.e("JSON", s);


                                Log.e("login", "2");

                                JSONObject result = jsonobj.getJSONObject("result");
                                String checkResult = result.getString("status");

                                Log.e("login", "3");

                                String getId;
                                if (checkResult.equals("success")) {

                                    Log.e("insidepost", checkResult);
                                    String get_api_secret = result.getString("api_secret");

                                    JSONObject user_data = result.getJSONObject("user_data");

                                    Log.e("user_data", user_data.toString());
                                    if (LoginActivity.decider.equals("2")) {
                                        getId = user_data.getString("non_member_id");
                                    } else {
                                        getId = user_data.getString("member_id");
                                    }

                                    String email = user_data.getString("email");
                                    String country = user_data.getString("country");
                                    String first_name = user_data.getString("first_name");
                                    String last_name = user_data.getString("last_name");
                                    String password = user_data.getString("password");
                                    String phNo;
                                    String role = "";

                                    if (user_data.getString("signin_type").equals("2")) {
                                        phNo = user_data.getString("contact_no");
                                    } else if (user_data.getString("signin_type").equals("1")) {
                                        phNo = user_data.getString("cell");
                                        role = user_data.getString("role");
                                    } else {
                                        phNo = "";
                                    }
                                    //  String up_image = user_data.getString("upload_image");


                                    Log.e("email", email);
                                    SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
                                    editor.putString("role", role);
                                    editor.putString("api_secret", get_api_secret);
                                    editor.putString("deciderId", getId);
                                    editor.putString("email", email);
                                    editor.putString("country", country);
                                    editor.putString("first_name", first_name);
                                    editor.putString("last_name", last_name);
                                    editor.putString("password", password);
                                    editor.putString("contact_no", phNo);
                                    //editor.putString("type", "member");
                                    editor.putString("stype", "member");

                                    // editor.putString("image", up_image);
                                    editor.apply();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                                    //    etMemberEmail.getEditText().setText("");
                                    //  etMemberPass.getEditText().setText("");

                                    progressDialog.dismiss();
                                }


                            } catch (JSONException e) {
                                Log.e("Error", e.getMessage());
                                // etMemberEmail.getEditText().setText("");
                                // etMemberPass.getEditText().setText("");
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                            }


                        }

                        progressDialog.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley_error", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

//                parameters.add(new BasicNameValuePair("email", getEmail));
//                parameters.add(new BasicNameValuePair("password", getPassword));
//                parameters.add(new BasicNameValuePair("signin_type", LoginActivity.decider));
//                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                parameters.add(new BasicNameValuePair("device_token", refreshedToken));
//
//
                params.put("email", getEmail);
                params.put("passowrd", getPassword);
                params.put("signin_type", LoginActivity.decider);
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                params.put("device_token", refreshedToken);


                Log.e("params", params.toString());

                return params;
            }
        };
        Volley.newRequestQueue(LoginActivity.this).add(request);

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private class Task extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Logging In");
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


            HttpPost post = new HttpPost(BASE_URL);
            Log.e("url", BASE_URL);

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("email", getEmail));
            parameters.add(new BasicNameValuePair("password", getPassword));
            parameters.add(new BasicNameValuePair("signin_type", LoginActivity.decider));
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            parameters.add(new BasicNameValuePair("device_token", refreshedToken));


            Log.e("params",parameters.toString());

            StringBuilder buffer = new StringBuilder();

            try {
                // Log.e("Insidegetjson", "insidetry");
                UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters);
                post.setEntity(encoded);
                HttpResponse response = httpClient.execute(post);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String Line = "";

                while ((Line = reader.readLine()) != null) {
                    // Log.e("reader", Line);
                    // Log.e("buffer", buffer.toString());
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


            if (s != null) {
                try {
                    Log.e("login", s);
                    JSONObject jsonobj = new JSONObject(s);
                    Log.e("JSON", s);


                    Log.e("login", "2");

                    JSONObject result = jsonobj.getJSONObject("result");
                    String checkResult = result.getString("status");

                    Log.e("login", "3");

                    String getId;
                    if (checkResult.equals("success")) {

                        Log.e("insidepost", checkResult);
                        String get_api_secret = result.getString("api_secret");

                        JSONObject user_data = result.getJSONObject("user_data");

                        Log.e("user_data", user_data.toString());
                        if (LoginActivity.decider.equals("2")) {
                            getId = user_data.getString("non_member_id");
                        } else {
                            getId = user_data.getString("member_id");
                        }

                        String email = user_data.getString("email");
                        String country = user_data.getString("country");
                        String first_name = user_data.getString("first_name");
                        String last_name = user_data.getString("last_name");
                        String password = user_data.getString("password");
                        String phNo;
                        String role = "";

                        if(user_data.getString("signin_type").equals("2"))
                        {
                            phNo   = user_data.getString("contact_no");
                        }
                        else if(user_data.getString("signin_type").equals("1"))
                        {
                            phNo   = user_data.getString("cell");
                            role = user_data.getString("role");
                        } else {
                            phNo = "";
                        }
                      //  String up_image = user_data.getString("upload_image");


                        Log.e("email", email);
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
                        editor.putString("role", role);
                        editor.putString("api_secret", get_api_secret);
                        editor.putString("deciderId", getId);
                        editor.putString("email", email);
                        editor.putString("country", country);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("password", password);
                        editor.putString("contact_no", phNo);
                        //editor.putString("type", "member");
                        editor.putString("stype", "member");

                       // editor.putString("image", up_image);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                    //    etMemberEmail.getEditText().setText("");
                      //  etMemberPass.getEditText().setText("");

                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                   // etMemberEmail.getEditText().setText("");
                   // etMemberPass.getEditText().setText("");
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                }


            }

            progressDialog.dismiss();
        }
    }




/*    public void hideOrShowLoginForms() {
        if (clMember.getVisibility() == View.VISIBLE) {
            llMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            llNonMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
//            tvNo
            clNonMember.setVisibility(View.VISIBLE);
            clMember.setVisibility(View.GONE);
        } else {
            llMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            llNonMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            clMember.setVisibility(View.VISIBLE);
            clNonMember.setVisibility(View.GONE);
        }
    }*/
}
