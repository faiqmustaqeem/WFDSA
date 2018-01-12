package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static java.security.AccessController.getContext;

/**
 * Created by Codiansoft on 12/11/2017.
 */

public class RegisterEvent extends AppCompatActivity {
    Spinner sp1, sp2, sp3;
    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    Toolbar toolbar;
    Button btn;
    ProgressDialog dialog;


    TextInputLayout tilFirstName;
    TextInputLayout tilAddress;
    TextInputLayout tilContact;
    TextInputLayout tilEmailEdit;
    TextInputLayout tilCardNumber;
    String year;
    String month;
    String countryName;
    EditText cvcnoedit;

    EditText tilFirstNameEdit;
    EditText tilAddressEdit;
    EditText tilContactEdit;
    EditText tilEmailEditedt;
    EditText tilCardNumberEdit;
    int monthToVerify;
    TextView eventname;
    TextView eventlocation;

    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    String addr, fname, contact, email, cardno, cvcno;

    public void clearFields() {
        tilFirstNameEdit.setText("");
        tilAddressEdit.setText("");
        tilCardNumberEdit.setText("");
        tilContactEdit.setText("");
        tilEmailEditedt.setText("");
        cvcnoedit.setText("");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_registration);
        eventname = (TextView) findViewById(R.id.eventname);
        eventlocation = (TextView) findViewById(R.id.eventlocation);

        eventname.setText(getIntent().getStringExtra("name"));
        eventlocation.setText(getIntent().getStringExtra("location"));


        dialog = new ProgressDialog(RegisterEvent.this);
        dialog.setTitle("Payment in progress");
        dialog.setTitle("Please Wait");

        tilAddress = (TextInputLayout) findViewById(R.id.tilAddress);
        tilFirstName = (TextInputLayout) findViewById(R.id.tilFirstName);
        tilContact = (TextInputLayout) findViewById(R.id.tilContact);
        tilEmailEdit = (TextInputLayout) findViewById(R.id.tilEmail);
        tilCardNumber = (TextInputLayout) findViewById(R.id.tilCardNumber);

        tilFirstNameEdit = (EditText) findViewById(R.id.etFirstName);
        tilAddressEdit = (EditText) findViewById(R.id.tilAddressEdit);
        tilCardNumberEdit = (EditText) findViewById(R.id.etCardNumber);
        tilContactEdit = (EditText) findViewById(R.id.tilContactEdit);
        tilEmailEditedt = (EditText) findViewById(R.id.tilEmailEdit);
        cvcnoedit = (EditText) findViewById(R.id.cvcnoedit);
        clearFields();


        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp3 = (Spinner) findViewById(R.id.spinner3);
        btn = (Button) findViewById(R.id.register);


        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int maxyears = thisYear + 20;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Log.e("year", String.valueOf(thisYear));

        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        for (int i = 1; i <= 32; i++) {
            days.add(Integer.toString(i));
        }


        for (int i = thisYear; i <= maxyears; i++) {
            //Log.e("year", String.valueOf(i));
            years.add(Integer.toString(i));

        }
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //        Toast.makeText(RegisterEvent.this, String.valueOf(parent.getItemIdAtPosition(position)), Toast.LENGTH_SHORT).show();
                year = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);

        sp2.setAdapter(adapter2);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = parent.getItemAtPosition(position).toString();
                monthToVerify = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp3.setAdapter(adapter3);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                cvcno = cvcnoedit.getText().toString();

                addr = tilAddress.getEditText().getText().toString();
                fname = tilFirstName.getEditText().getText().toString();
                contact = tilContact.getEditText().getText().toString();
                email = tilEmailEdit.getEditText().getText().toString();
                cardno = tilCardNumber.getEditText().getText().toString();


                Card card = new Card(
                        "4242424242424242",
                        monthToVerify,
                        Integer.valueOf(year),
                        cvcno
                );
                Log.e("CVC", card.getCVC());

                card.setName("Jenny Rosen");

                //card.setCurrency("usd");

                //begin transaction
                Stripe stripe = new Stripe(RegisterEvent.this, "pk_test_hBDKg0otup1IdPx0qS2o29Fl");
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(final Token token) {
                                dialog.dismiss();
                                Log.e("StripeToken", token.getId());
                                Log.e("PureToken", token.toString());
                                Toast.makeText(RegisterEvent.this, token.getId(), Toast.LENGTH_SHORT).show();

                                StringRequest request = new StringRequest(Request.Method.POST, "http://codiansoft.com/wfdsa/apis/payment/Payment_Verification", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject job = new JSONObject(response);
                                            String resp = job.getString("response");
                                            if (resp.equals("Successful")) {
                                                Toast.makeText(RegisterEvent.this, "Payment Transaction Completed!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterEvent.this, "Payment Transaction Failed!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            Log.e("ErrorMessage", e.getMessage());
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

                                        params.put("api_secret", prefs.getString("api_secret", ""));
                                        params.put("stripe_token", token.getId());
                                        params.put("amount", String.valueOf(500));
                                        params.put("user_id", MainActivity.getId);
                                        params.put("signin_type", LoginActivity.decider);

                                        Log.e("api_sec", prefs.getString("api_secret", ""));
                                        Log.e("stripe_token", token.getId());
                                        Log.e("amount", String.valueOf(500));
                                        Log.e("user_id", MainActivity.getId);
                                        Log.e("signin_type", LoginActivity.decider);

                                        return params;
                                    }
                                };
                                Volley.newRequestQueue(RegisterEvent.this).add(request);
                            }

                            public void onError(Exception error) {
                                // Show localized error message
                                // Toast.makeText(RegisterEvent.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("ExceptionFromStripe", error.getMessage());
                                Log.e("ExceptionFromStripe", error.getLocalizedMessage());
                                dialog.dismiss();

                            }
                        }
                );


//                tilAddress.getEditText().setText("");
//                tilFirstName.getEditText().setText("");
//                tilContact.getEditText().setText("");
//                tilEmailEdit.getEditText().setText("");
//                tilCardNumber.getEditText().setText("");
//                cvcnoedit.setText("");

                if (!tilAddressEdit.getText().toString().equals("") && !tilFirstNameEdit.getText().toString().equals("") && !tilContactEdit.getText().toString().equals("")
                        && !tilCardNumberEdit.getText().toString().equals("") && !tilEmailEditedt.getText().toString().equals("") && !cvcnoedit.getText().toString().equals("")) {

                    Dialog dialog = new Dialog(RegisterEvent.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                    dialog.getWindow().setLayout(lp.width, lp.height);
                    dialog.setContentView(R.layout.thanking_activity);
                    dialog.show();
                    Log.e("addr", addr);
                    Log.e("fname", fname);
                    Log.e("contact", contact);
                    Log.e("email", email);
                    Log.e("cardno", cardno);
                    Log.e("year", year);
                    Log.e("month", month);
                    Log.e("country", countryName);
                    clearFields();


                } else {
                    Log.e("addr", addr);
                    Log.e("fname", fname);
                    Log.e("contact", contact);
                    Log.e("email", email);
                    Log.e("cardno", cardno);
                    Log.e("year", year);
                    Log.e("month", month);
                    Log.e("country", countryName);


                    Toast.makeText(RegisterEvent.this, "Fill all the fields first!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
