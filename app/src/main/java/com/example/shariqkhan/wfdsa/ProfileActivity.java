package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shariqkhan.wfdsa.Dialog.ProfileEditPermissionDialog;
import com.example.shariqkhan.wfdsa.Singleton.MySingleton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvSignOut)
    TextView tvSignOut;
    @BindView(R.id.tvEditOrUpdate)
    TextView tvEditOrUpdate;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;

    public static final int GALLERY_CONSTANT = 1;

    ProfileEditPermissionDialog d;
    public static boolean canEdit = false;
    static String newFirstName;
    static String newLastName;
    static String newEmail;
    public static Uri uri;
    Bitmap bmp;

    String BASE_URL = "http://codiansoft.com/wfdsa/apis/member/Edit_member";
    public static String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e("email", MainActivity.getEmail);

//


        ButterKnife.bind(this);

        initUI();
        newFirstName = MainActivity.getFirstName;
        newLastName = MainActivity.getLastName;
        newEmail = MainActivity.getEmail;


        etEmail.setText(MainActivity.getEmail);
        etLastName.setText(MainActivity.getLastName);
        etFirstName.setText(MainActivity.getFirstName);
        etMobileNumber.setText(MainActivity.phoneNo);



        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (tvEditOrUpdate.getText().toString().equals("EDIT")) {
                    makeFieldsEditable();
                    tvEditOrUpdate.setText("SAVE");

                }
            }
        });

        etEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(ProfileActivity.this, "You cannot edit your email!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newFirstName = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newLastName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }



    private void makeFieldsEditable() {
        etFirstName.setEnabled(true);
        etLastName.setEnabled(true);
        etEmail.setEnabled(false);
        etMobileNumber.setEnabled(true);
    }

    private void initUI() {
        d = new ProfileEditPermissionDialog(this, MainActivity.password);
        ivBack.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);
        tvEditOrUpdate.setOnClickListener(this);


        // Picasso.with(this).load("https://qph.ec.quoracdn.net/main-thumb-64803074-50-dxlthhqswgcemtdghaduorrgglmdgifa.jpeg").into(profile_image);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_CONSTANT);
            */
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfileActivity.this);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    path = convertTOString(bmp);
                    Log.e("Path", path);


                } catch (IOException e) {
                    Log.e("ExceptionFoundOfImage", e.getMessage());

                    e.printStackTrace();
                }


                Log.e("Uri", String.valueOf(uri));
                profile_image.setImageURI(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @OnClick({R.id.ivBack, R.id.tvSignOut, R.id.tvEditOrUpdate})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tvSignOut:
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Are you sure to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /*SharedPreferences settings = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("apiSecretKey", "");
                                editor.putString("userID", "");
                                editor.commit();*/

                                Intent logoutIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                                dialog.dismiss();
                                ActivityCompat.finishAffinity(ProfileActivity.this);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ProfileActivity.this.startActivity(logoutIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
                break;
            case R.id.tvEditOrUpdate:
                if (tvEditOrUpdate.getText().toString().equals("EDIT")) {
                    d.show();


                } else if (tvEditOrUpdate.getText().toString().equals("SAVE")) {
                    Task task = new Task();
                    task.execute();
                }
                break;

        }
    }



         class Task extends AsyncTask<String, Void, String> {
            String stream = null;
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setTitle("Changing profile information");

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

//                params.put("last_name", etLastName.getText().toString());
//                params.put("cell", etMobileNumber.getText().toString());
//                params.put("id", MainActivity.getId);
//                params.put("image", path)
                parameters.add(new BasicNameValuePair("first_name", etFirstName.getText().toString()));
                parameters.add(new BasicNameValuePair("last_name", etFirstName.getText().toString()));

                parameters.add(new BasicNameValuePair("cell", etMobileNumber.getText().toString()));
                parameters.add(new BasicNameValuePair("id", MainActivity.getId));
                parameters.add(new BasicNameValuePair("image", path));

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
//
//                            Log.e("insidepost", checkResult);
//                            String get_api_secret = result.getString("api_secret");
//
//                            JSONObject user_data = result.getJSONObject("user_data");
//
//                            String getId = user_data.getString("non_member_id");
//                            String email = user_data.getString("email");
//                            String country = user_data.getString("country");
//                            String first_name = user_data.getString("first_name");
//                            String last_name = user_data.getString("last_name");
//                            String password = user_data.getString("password");
//                            String phNo = user_data.getString("contact_no");
//                            Log.e("email", email);
                            SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();

                            editor.putString("first_name",etFirstName.getText().toString());
                            editor.putString("last_name",etLastName.getText().toString());
                           editor.putString("contact_no", etMobileNumber.getText().toString());


                            editor.apply();

                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {


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

//        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("HelloWorld", "HeloWorld");
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Log.e("HelloWorld", "HeloWorld");
//                    JSONObject job = jsonObject.getJSONObject("result");
//                    if (job.getString("response").equals("success")) {
//
//                        SharedPreferences.Editor edit = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
//                        edit.putString("first_name", etFirstName.getText().toString());
//                        edit.putString("last_name", etLastName.getText().toString());
//                        edit.putString("contact_no", etMobileNumber.getText().toString());
//                        edit.putString("profile_image", path);
//
//
//
//                        edit.apply();
//
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("Exception", e.getMessage());
//                }
//
//                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("first_name", etFirstName.getText().toString());
//                params.put("last_name", etLastName.getText().toString());
//                params.put("cell", etMobileNumber.getText().toString());
//                params.put("id", MainActivity.getId);
//                params.put("image", path);
//                return params;
//            }
//        };
//        MySingleton.getInstance(ProfileActivity.this).addToRequestQueue(request);





    public boolean isValidEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();

    }

    private String convertTOString(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}

