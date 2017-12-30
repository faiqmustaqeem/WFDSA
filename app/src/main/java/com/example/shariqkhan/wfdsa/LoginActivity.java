package com.example.shariqkhan.wfdsa;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.shariqkhan.wfdsa.SplashActivity.fetchAnnouncements;
import static com.example.shariqkhan.wfdsa.SplashActivity.fetchEvents;


public class LoginActivity extends AppCompatActivity {
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

    TextInputLayout etMemberEmail;
    TextInputLayout etMemberPass;
    ImageView ivBack;
    String getEmail;
    String getPassword;
    android.app.ActionBar actionbar;

    public static String BASE_URL = "http://www.codiansoft.com/wfdsa/api/login";

    public static String decider = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Picasso.with(this).load("http://wfdsa.org/wp-content/uploads/2016/02/logo.jpg").into(ivWFDSALogo);

        etMemberEmail = (TextInputLayout) findViewById(R.id.tilMemberEmail);
        etMemberPass = (TextInputLayout) findViewById(R.id.tilMemberPassword);

        tvMemberSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEmail = etMemberEmail.getEditText().getText().toString();
                getPassword = etMemberPass.getEditText().getText().toString();
                Log.e("Email", getEmail);
                Log.e("Password", getPassword);

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
                llMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                clMember.setVisibility(View.VISIBLE);
                clNonMember.setVisibility(View.GONE);
                break;
            case R.id.tvNonMember:
                decider = "2";
                llMember.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                llNonMember.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                clNonMember.setVisibility(View.VISIBLE);
                clMember.setVisibility(View.GONE);
                break;
            case R.id.tvNonMemberSignUp:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.tvNonMemberSignIn:
                getEmail = etMemberEmail.getEditText().getText().toString();
                getPassword = etMemberPass.getEditText().getText().toString();
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
                break;
        }
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
            Log.e("Must", "Must");

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("email", getEmail));
            parameters.add(new BasicNameValuePair("password", getPassword));
            parameters.add(new BasicNameValuePair("signin_type", LoginActivity.decider));

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
                    String getId;
                    if (checkResult.equals("success")) {

                        Log.e("insidepost", checkResult);
                        String get_api_secret = result.getString("api_secret");

                        JSONObject user_data = result.getJSONObject("user_data");

                        if (LoginActivity.decider.equals("2"))
                        {
                             getId = user_data.getString("non_member_id");
                        }else{
                            getId = user_data.getString("member_id");
                        }

                        String email = user_data.getString("email");
                        String country = user_data.getString("country");
                        String first_name = user_data.getString("first_name");
                        String last_name = user_data.getString("last_name");
                        String password = user_data.getString("password");
                        String phNo = user_data.getString("cell");

                        Log.e("email", email);
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                        editor.putString("api_secret", get_api_secret);
                        editor.putString("deciderId", getId);
                        editor.putString("email", email);
                        editor.putString("country", country);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("password", password);
                        editor.putString("contact_no", phNo);
                        editor.putString("type","member");
                        editor.putString("stype", "member");

                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                        etMemberEmail.getEditText().setText("");
                        etMemberPass.getEditText().setText("");

                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage", e.getMessage());
                    etMemberEmail.getEditText().setText("");
                    etMemberPass.getEditText().setText("");
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
