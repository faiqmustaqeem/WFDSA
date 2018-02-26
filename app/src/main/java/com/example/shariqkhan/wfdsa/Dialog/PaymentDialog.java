package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.LoginActivity;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.MyInvoices;
import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.RegisterEvent;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Codiansoft on 10/27/2017.
 */

public class PaymentDialog extends Dialog implements View.OnClickListener{

    public Context act;
    public Dialog d;

    EditText name;
    EditText cardNumber;
    EditText cvcNo;
    EditText email;
    Spinner monthSpinner;
    Spinner yearSpinner;
    String year;
    String month;

    ProgressDialog dialog;
    TextView payNow;
    String token_id;

    String invoice_title;
    String invoice_fees;
    String invoice_id;
    TextView amount;
    int rv_index;

    public PaymentDialog(Context a, String invoice_title, String invoice_fees, String invoice_id, int rv_index) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        this.invoice_title = invoice_title;
        this.invoice_fees = invoice_fees;
        this.invoice_id = invoice_id;
        this.rv_index = rv_index;
        Log.e("invoice_id", invoice_id);
//        Dialog dialog = new Dialog(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_payment);
        setCanceledOnTouchOutside(true);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText(invoice_title);
        amount = findViewById(R.id.tvAmount);
        amount.setText(invoice_fees);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        initUI();

        name = (EditText) findViewById(R.id.etName);
        cardNumber = (EditText) findViewById(R.id.etCardNumber);
        cvcNo = (EditText) findViewById(R.id.etCVCcode);
        email = (EditText) findViewById(R.id.etEmail);

        monthSpinner = findViewById(R.id.spCardExpiryMonth);
        yearSpinner = findViewById(R.id.spCardExpiryYear);


        dialog = new ProgressDialog(act);
        dialog.setTitle("Payment in progress");
        dialog.setTitle("Please Wait");


        ArrayList<String> years = new ArrayList<>();
        ArrayList<String> months = new ArrayList<>();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int maxyears = thisYear + 20;

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

        for (int i = thisYear; i <= maxyears; i++) {
            years.add(Integer.toString(i));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(adapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //        Toast.makeText(RegisterEvent.this, String.valueOf(parent.getItemIdAtPosition(position)), Toast.LENGTH_SHORT).show();
                month = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(adapter1);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //        Toast.makeText(RegisterEvent.this, String.valueOf(parent.getItemIdAtPosition(position)), Toast.LENGTH_SHORT).show();
                year = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        payNow = findViewById(R.id.tvPayNow);

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                    dialog.show();


                    Card card = new Card(
                            "4242424242424242",
                            11,
                            2019,
                            cvcNo.getText().toString()
                    );
                    Log.e("CVC", card.getCVC());

                    if (!card.validateCard()) {
                        Toast.makeText(act, "Invalid card number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    card.setName(name.getText().toString());

                    Stripe stripe = new Stripe(act, "pk_test_5fWYZdG9SUj4KUEuJ8FBO71Q");
                    stripe.createToken(
                            card,
                            new TokenCallback() {
                                public void onSuccess(final Token token) {

//                                    Log.e("StripeToken", token.getId());
//                                    Log.e("PureToken", token.toString());
                                    //  Toast.makeText(RegisterEvent.this, "there", Toast.LENGTH_SHORT).show();

                                    //there
                                    token_id = token.getId();
                                    Log.e("agaya", "agaya");
                                    sendData();

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


                }
            }
        });



    }

    private boolean checkFields() {
        if (name.getText().toString().equals("")) {
            printMsg("Enter name");
            return false;
        }
        if (cardNumber.getText().toString().equals("")) {
            printMsg("Enter card number");
            return false;
        }
        if (cvcNo.getText().toString().equals("")) {
            printMsg("Enter CVC number");
            return false;
        }
        if (email.getText().toString().equals("")) {
            printMsg("Enter Email");
            return false;
        }
        if (month.equals("")) {
            printMsg("select card expiry Month");
            return false;
        }
        if (year.equals("")) {
            printMsg("select card expiry Year");
            return false;
        }
        return true;
    }

    public void sendData() {

        if (checkFields()) {
            StringRequest request = new StringRequest(Request.Method.POST, GlobalClass.base_url + "wfdsa/apis/payment/invoice_Verification",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {


                                dialog.dismiss();

                                JSONObject job = new JSONObject(response);
                                JSONObject result = job.getJSONObject("result");
                                String res = result.getString("response");
                                Log.e("response", res);

                                if (res.equals("Payment Successfully Paid")) {


                                    final MaterialDialog materialDialog = new MaterialDialog.Builder(act)
                                            .title("Successfully Paid")
                                            .content("you have successfully paid for " + invoice_title)
                                            .positiveText("OK")
                                            .canceledOnTouchOutside(false)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    MyInvoices.paymentsList.get(rv_index).setType("1");
                                                    MyInvoices.invoiceAdapter.notifyItemChanged(rv_index);
                                                    dismiss();

                                                }
                                            })
                                            .show();


                                } else {
                                    Toast.makeText(act, "Payment Transaction Failed!", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                    // finish();
                                }
                            } catch (JSONException e) {
                                Log.e("ErrorMessage", e.getMessage());
                                e.printStackTrace();
                                dismiss();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.e("Volley_error", error.getMessage());
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Log.e("Volley_error", error.getMessage());
                            } else {
                                Toast.makeText(act, "You application is not connected to internet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences prefs = act.getSharedPreferences("SharedPreferences", MODE_PRIVATE);

                    params.put("api_secret", prefs.getString("api_secret", ""));
                    params.put("stripe_token", token_id);
                    params.put("amount", invoice_fees);
                    params.put("user_id", MainActivity.getId);
                    params.put("signin_type", MainActivity.SIGNIN_TYPE);
                    params.put("invoice_id", invoice_id);

                    Log.e("params", params.toString());

                    return params;
                }
            };

            Volley.newRequestQueue(act).add(request);
            // AppSingleton.getInstance().addToRequestQueue(request, "Payment");

        }
    }

    void printMsg(String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.tvCancel:
                dismiss();
                break;*/
        }
    }
}
