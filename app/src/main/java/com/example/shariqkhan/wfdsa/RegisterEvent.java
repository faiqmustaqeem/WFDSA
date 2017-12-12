package com.example.shariqkhan.wfdsa;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;


import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

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
            Log.e("year", String.valueOf(i));
            years.add(Integer.toString(i));

        }
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
                cvcno = cvcnoedit.getText().toString();

                addr = tilAddress.getEditText().getText().toString();
                fname = tilFirstName.getEditText().getText().toString();
                contact = tilContact.getEditText().getText().toString();
                email = tilEmailEdit.getEditText().getText().toString();
                cardno = tilCardNumber.getEditText().getText().toString();

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
