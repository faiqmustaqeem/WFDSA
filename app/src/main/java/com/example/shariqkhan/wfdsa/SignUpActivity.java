

package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Dialog.TermsAndConditionsDialog;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.shariqkhan.wfdsa.LoginActivity.BASE_URL;


public class SignUpActivity extends AppCompatActivity {
    ImageView ivBack;

    @BindView(R.id.tvTermsAndConditions)
    TextView tvTermsAndConditions;
    @BindView(R.id.spCountry)
    Spinner spCountry;

    TextInputLayout tilFirstName;
    TextInputLayout tilLastName;
    TextInputLayout tilContactNum;
    TextInputLayout tilEmail;
    TextInputLayout tilPassword;
    TextInputLayout tilConfirmPassword;

    TextView tvSignUp;

    String firstName;
    String lastName;
    String contactNum;
    String email;
    String password;
    String confirmPassword;

    String getItem;
    public String BASE_URL = "http://www.codiansoft.com/wfdsa/api/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        tilFirstName = (TextInputLayout) findViewById(R.id.tilFirstName);
        tilLastName = (TextInputLayout) findViewById(R.id.tilLastName);
        tilContactNum = (TextInputLayout) findViewById(R.id.tilContactNum);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.tilConfrimPass);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tilFirstName.getEditText().getText().toString().equals("") &&
                        !tilLastName.getEditText().getText().toString().equals("")
                        && tilContactNum.getEditText().getText().toString().equals("") &&
                        tilEmail.getEditText().getText().toString().equals("") &&
                        tilPassword.getEditText().getText().toString().equals("")
                        && tilConfirmPassword.getEditText().getText().toString().equals("")
                        ) {
                    Toast.makeText(SignUpActivity.this, "Fill in the fields", Toast.LENGTH_SHORT).show();
                } else {



                firstName = tilFirstName.getEditText().getText().toString();
                if (firstName.equals(""))
                    Toast.makeText(SignUpActivity.this, "First name empty!", Toast.LENGTH_SHORT).show();
                lastName = tilLastName.getEditText().getText().toString();
                if (lastName.equals(""))
                    Toast.makeText(SignUpActivity.this, "Last name empty!", Toast.LENGTH_SHORT).show();
                contactNum = tilContactNum.getEditText().getText().toString();
                if (contactNum.equals(""))
                    Toast.makeText(SignUpActivity.this, "Contact number empty!", Toast.LENGTH_SHORT).show();
                email = tilEmail.getEditText().getText().toString();
                if (email.equals(""))
                    Toast.makeText(SignUpActivity.this, "Email empty!", Toast.LENGTH_SHORT).show();
                password = tilPassword.getEditText().getText().toString();
                confirmPassword = tilConfirmPassword.getEditText().getText().toString();


                if (!password.equals(confirmPassword))
                    Toast.makeText(SignUpActivity.this, "Invalid password confirmation!", Toast.LENGTH_SHORT).show();

                if (!firstName.equals("") && !lastName.equals("") && !contactNum.equals("") && !email.equals("") && (password.equals(confirmPassword))) {
                    Task task = new Task();
                    task.execute();

                }
            }

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Sign Up");

        ivBack = (ImageView) findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.this.finish();
            }
        });
        initUI();
        initCountriesSpinner();
    }

    private void initUI() {

    }

    private void initCountriesSpinner() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        spCountry.setAdapter(adapter);


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getItem = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.tvTermsAndConditions})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTermsAndConditions:
                TermsAndConditionsDialog d = new TermsAndConditionsDialog(this);
                d.show();
                break;
        }
    }

    private class Task extends AsyncTask<String, Void, String> {
        String stream = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SignUpActivity.this);
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
            parameters.add(new BasicNameValuePair("first_name", firstName));
            parameters.add(new BasicNameValuePair("last_name", lastName));
            parameters.add(new BasicNameValuePair("email", email));
            parameters.add(new BasicNameValuePair("country", getItem));
            parameters.add(new BasicNameValuePair("contact", contactNum));
            parameters.add(new BasicNameValuePair("password", password));
            parameters.add(new BasicNameValuePair("confirm_password", confirmPassword));

            Log.e("f", firstName);
            Log.e("l", lastName);
            Log.e("e", email);
            Log.e("c", getItem);
            Log.e("c", contactNum);
            Log.e("p", password);
            Log.e("c", confirmPassword);

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

                        Log.e("insidepost", checkResult);
                        String get_api_secret = result.getString("api_secret");

                        JSONObject user_data = result.getJSONObject("user_details");

                        String getId = user_data.getString("non_member_id");
                        String email = user_data.getString("email");
                        String getpass = user_data.getString("password");

                        Log.e("email", email);
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                        editor.putString("api_secret", get_api_secret);
                        editor.putString("deciderId", getId);
                        editor.putString("email", email);
                        editor.apply();

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Email already exist or invalid password confirmation!!", Toast.LENGTH_LONG).show();

                        tilEmail.getEditText().setText("");
                        tilPassword.getEditText().setText("");
                        tilConfirmPassword.getEditText().setText("");

                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    Log.e("ErrorMessage", e.getMessage());
                    tilFirstName.getEditText().setText("");
                    tilLastName.getEditText().setText("");
                    tilContactNum.getEditText().setText("");
                    tilEmail.getEditText().setText("");
                    tilPassword.getEditText().setText("");
                    tilConfirmPassword.getEditText().setText("");
                    progressDialog.dismiss();
                }


            }

            progressDialog.dismiss();
        }
    }


}