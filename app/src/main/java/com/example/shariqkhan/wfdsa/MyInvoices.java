package com.example.shariqkhan.wfdsa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shariqkhan.wfdsa.Adapter.InvoiceAdapter;
import com.example.shariqkhan.wfdsa.Adapter.MyInvoicesAdapter;
import com.example.shariqkhan.wfdsa.Adapter.PaymentsRVAdapter;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInvoices extends AppCompatActivity {

    public static String filterableString = "";
    public static MyInvoicesAdapter invoiceAdapter;
    public static ArrayList<PaymentModel> paymentsList = new ArrayList<PaymentModel>();
    @BindView(R.id.rvPayments)
    RecyclerView rvPayments;
    Toolbar toolbar;
    ImageView imageFilter;
    ImageView image;
    ArrayList<PaymentModel> arrayListSave;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invoices);

        ButterKnife.bind(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageFilter = (ImageView) toolbar.findViewById(R.id.filter);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TaskAllInVoices task = new TaskAllInVoices();
        task.execute();


        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"Paid", "Unpaid", "All"};
                new MaterialDialog.Builder(MyInvoices.this)
                        .title("Invoice Type")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                filterableString = text.toString();
                                Log.e("string", filterableString);
                                if (paymentsList.size() < 1) {

                                } else {
                                    if (filterableString.equals("Paid")) {
                                        paymentsList.clear();
                                        for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                            PaymentModel memberToCheck = new PaymentModel();
                                            memberToCheck = arrayListSave.get(counter);
                                            Log.e("MEMBER", String.valueOf(memberToCheck));

                                            if (memberToCheck.type.equals("1")) {

                                                Log.e("SizeOfAmerica", String.valueOf(paymentsList.size()));
                                                paymentsList.add(memberToCheck);
                                                Log.e("SizeOfAmerica", String.valueOf(paymentsList.size()));

                                            }

                                        }
                                        if (paymentsList.size() > 0) {
                                            invoiceAdapter = new MyInvoicesAdapter(MyInvoices.this, paymentsList);
                                            rvPayments.setAdapter(invoiceAdapter);
                                        }

                                    } else if (filterableString.equals("All")) {
                                        paymentsList.clear();
                                        paymentsList = new ArrayList<>(arrayListSave);
                                        Log.e("SizeAll", String.valueOf(paymentsList.size()));
                                        invoiceAdapter = new MyInvoicesAdapter(MyInvoices.this, arrayListSave);
                                        rvPayments.setAdapter(invoiceAdapter);

                                    } else if (filterableString.equals("Unpaid")) {
                                        paymentsList.clear();
                                        for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                            PaymentModel memberToCheck = new PaymentModel();
                                            memberToCheck = arrayListSave.get(counter);
                                            Log.e("MEMBER", String.valueOf(memberToCheck));

                                            if (memberToCheck.type.equals("0")) {

                                                Log.e("SizeOfPak", String.valueOf(paymentsList.size()));
                                                paymentsList.add(memberToCheck);
                                                Log.e("SizeOfPak", String.valueOf(paymentsList.size()));

                                            }
                                        }
                                        Log.e("Jugar", String.valueOf(paymentsList.size()));
                                        invoiceAdapter = new MyInvoicesAdapter(MyInvoices.this, paymentsList);
                                        rvPayments.setAdapter(invoiceAdapter);

                                    }

                                }


                                //  Toast.makeText(MyPaymentActivity.this, text, Toast.LENGTH_SHORT).show();

                                return true;

                            }
                        })
                        .positiveText("Choose")
                        .show();


            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    public class TaskAllInVoices extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = GlobalClass.base_url + "wfdsa/apis/Payment/All_invoices_by_role?user_id=" + MainActivity.getId;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null)
            {
                Log.e("Res", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    paymentsList.clear();
                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");

                    if (getstatus.equals("success")) {
                        JSONArray rolesArray = resultObj.getJSONArray("payment_data");
                        //    roleArray = new String[rolesArray.length()];


                        for (int i = 0; i < rolesArray.length(); i++) {
                            PaymentModel model = new PaymentModel();
                            JSONObject obj = rolesArray.getJSONObject(i);

                            // model.setId(obj.getString("payment_id"));
                            model.setInvoice_id(obj.getString("invoice_id"));
                            // model.setDueDate(obj.getString("payment_date"));
                            model.setTitle(obj.getString("title"));
                            model.setType(obj.getString("payment_status"));
                            model.setAmount(obj.getString("grand_total"));
                            //model.setTitle("EVENT PAYMENT");

                            paymentsList.add(model);

                        }
                        Collections.reverse(paymentsList);
                        arrayListSave = new ArrayList<>(paymentsList);
                        invoiceAdapter = new MyInvoicesAdapter(MyInvoices.this, paymentsList);
                        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvPayments.setLayoutManager(mAnnouncementLayoutManager);
                        rvPayments.setItemAnimator(new DefaultItemAnimator());
                        rvPayments.setAdapter(invoiceAdapter);


                        //  fetchMyPayments();


                    }
                    progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
            else {
                Toast.makeText(MyInvoices.this , "you are not connected to internet !" , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MyInvoices.this);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}
