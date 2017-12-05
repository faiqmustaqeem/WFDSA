package com.example.shariqkhan.wfdsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

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