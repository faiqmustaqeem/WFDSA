package com.example.shariqkhan.wfdsa;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Dialog.TermsAndConditionsDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

                firstName = tilFirstName.getEditText().getText().toString();
                lastName = tilLastName.getEditText().getText().toString();
                contactNum = tilContactNum.getEditText().getText().toString();
                email = tilEmail.getEditText().getText().toString();
                password = tilPassword.getEditText().getText().toString();
                confirmPassword = tilConfirmPassword.getEditText().getText().toString();


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

        spCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getItem = parent.getItemAtPosition(position).toString();
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
}