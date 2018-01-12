

package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Dialog.TermsAndConditionsDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @BindView(R.id.cbAcceptTerms)
    CheckBox cbAcceptTerms;

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
    ProgressDialog dialog;
    String confirmPassword;


    LoginButton tvNonMemberFBSignIn;

    CallbackManager callbackManager;

    String getItem;
    public String BASE_URL = "http://www.codiansoft.com/wfdsa/api/register";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);


        printKeyhash();
        callbackManager = CallbackManager.Factory.create();

        tilFirstName = (TextInputLayout) findViewById(R.id.tilFirstName);
        tilLastName = (TextInputLayout) findViewById(R.id.tilLastName);
        tilContactNum = (TextInputLayout) findViewById(R.id.tilContactNum);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.tilConfrimPass);

        tvNonMemberFBSignIn = (LoginButton) findViewById(R.id.tvNonMemberFBSignIn);
        tvNonMemberFBSignIn.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        tvNonMemberFBSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setTitle("Retreiving");
                dialog.show();

                String accessToken = loginResult.getAccessToken().getToken();


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        dialog.dismiss();
                        Log.e("Response", object.toString());

                        Log.e("email", object.optString("email"));
                        Log.e("first_name", object.optString("first_name"));

                        SharedPreferences.Editor prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
                        prefs.putString("stype", "fb");
                        prefs.putString("api_secret", "not_null");
                        prefs.putString("deciderId", "nonmember");
                        prefs.putString("type", "nonmember");
                        prefs.putString("first_name", object.optString("first_name"));
                        prefs.putString("last_name", object.optString("last_name"));
                        prefs.putString("email", object.optString("email"));

                        prefs.apply();


                        // Toast.makeText(SignUpActivity.this, object.getString("email"), Toast.LENGTH_SHORT).show();
                        LoginActivity.decider = "2";
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        //  txt1.setText(object.getString("email"));

                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,name,first_name,last_name,email");
                request.setParameters(bundle);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error", error.getMessage());
                dialog.dismiss();

            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            //txt1.setText(AccessToken.getCurrentAccessToken().getUserId());
            Log.e("Inside here", "Accesss");
        }


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

                    if (password.length() < 5) {
                        Toast.makeText(SignUpActivity.this, "Password should be atleast of 5 characters or numbers!", Toast.LENGTH_SHORT).show();
                        tilPassword.getEditText().setText("");
                        tilConfirmPassword.getEditText().setText("");

                    }
                    if (!password.equals(confirmPassword))
                        Toast.makeText(SignUpActivity.this, "Invalid password confirmation!", Toast.LENGTH_SHORT).show();


                    if (!firstName.equals("") && !lastName.equals("") && !contactNum.equals("") && !email.equals("") && (password.equals(confirmPassword))) {


                        if (isValidEmail(email)) {
                            Task task = new Task();
                            task.execute();

                        } else {
                            Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            tilEmail.getEditText().setText("");
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Check if all details are filled!", Toast.LENGTH_SHORT).show();
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
        countries.add("");
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
            progressDialog.setTitle("Signing Up");

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

                        String country = user_data.getString("country");
                        String first_name = user_data.getString("first_name");
                        String last_name = user_data.getString("last_name");
                        String contact_no = user_data.getString("contact_no");

                        Log.e("email", email);
                        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                        editor.putString("api_secret", get_api_secret);
                        editor.putString("deciderId", getId);
                        editor.putString("email", email);
                        editor.putString("country", country);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("password", getpass);
                        editor.putString("contact_no", contact_no);
                        editor.putString("type", "nonmember");
                        editor.putString("stype", "nonmember");
                        editor.apply();
                        LoginActivity.decider = "2";

                        Toast.makeText(SignUpActivity.this, "Check your account to get dummy email password!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Email already exist or invalid password confirmation!!", Toast.LENGTH_LONG).show();

                        tilPassword.getEditText().setText("");
                        tilConfirmPassword.getEditText().setText("");
                        tilEmail.getEditText().setText("");

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

    private void printKeyhash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.shariqkhan.wfdsa", PackageManager.GET_SIGNATURES);
            for (Signature sign : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(sign.toByteArray());
                Log.e("Hash-key", Base64.encodeToString(md.digest(), Base64.DEFAULT));


            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public boolean isValidEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}