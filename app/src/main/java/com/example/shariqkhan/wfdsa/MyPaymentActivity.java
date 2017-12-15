package com.example.shariqkhan.wfdsa;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shariqkhan.wfdsa.Adapter.PaymentsRVAdapter;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.google.android.gms.wallet.Payments;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPaymentActivity extends AppCompatActivity {

    @BindView(R.id.rvPayments)
    RecyclerView rvPayments;
    PaymentsRVAdapter paymentsRVAdapter;
    Toolbar toolbar;
    ImageView imageFilter;
    ImageView image;
    ArrayList<PaymentModel> paymentsList = new ArrayList<PaymentModel>();
    ArrayList<PaymentModel> arrayListSave;
    public static String filterableString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
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


        paymentsRVAdapter = new PaymentsRVAdapter(this, paymentsList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPayments.setLayoutManager(mAnnouncementLayoutManager);
        rvPayments.setItemAnimator(new DefaultItemAnimator());
        rvPayments.setAdapter(paymentsRVAdapter);

        fetchMyPayments();

        arrayListSave = new ArrayList<>(paymentsList);


        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"paid", "unpaid", "All"};
                new MaterialDialog.Builder(MyPaymentActivity.this)
                        .title("Invoice Type")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                filterableString = text.toString();
                                Log.e("string", filterableString);
                                if (filterableString.equals("paid")) {
                                    paymentsList.clear();
                                    for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                        PaymentModel memberToCheck = new PaymentModel();
                                        memberToCheck = arrayListSave.get(counter);
                                        Log.e("MEMBER", String.valueOf(memberToCheck));

                                        if (memberToCheck.id.equals(filterableString)) {

                                            Log.e("SizeOfAmerica", String.valueOf(paymentsList.size()));
                                            paymentsList.add(memberToCheck);
                                            Log.e("SizeOfAmerica", String.valueOf(paymentsList.size()));

                                        }

                                    }

                                    paymentsRVAdapter = new PaymentsRVAdapter(MyPaymentActivity.this, paymentsList);
                                    rvPayments.setAdapter(paymentsRVAdapter);

                                } else if (filterableString.equals("All")) {
                                    paymentsList.clear();
                                    paymentsList = new ArrayList<>(arrayListSave);
                                    Log.e("SizeAll", String.valueOf(paymentsList.size()));
                                    paymentsRVAdapter = new PaymentsRVAdapter(MyPaymentActivity.this, arrayListSave);
                                    rvPayments.setAdapter(paymentsRVAdapter);

                                } else if (filterableString.equals("unpaid")) {
                                    paymentsList.clear();
                                    for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                        PaymentModel memberToCheck = new PaymentModel();
                                        memberToCheck = arrayListSave.get(counter);
                                        Log.e("MEMBER", String.valueOf(memberToCheck));

                                        if (memberToCheck.id.equals(filterableString)) {

                                            Log.e("SizeOfPak", String.valueOf(paymentsList.size()));
                                            paymentsList.add(memberToCheck);
                                            Log.e("SizeOfPak", String.valueOf(paymentsList.size()));

                                        }
                                    }
                                    Log.e("Jugar", String.valueOf(paymentsList.size()));
                                    paymentsRVAdapter = new PaymentsRVAdapter(MyPaymentActivity.this, paymentsList);
                                    rvPayments.setAdapter(paymentsRVAdapter);

                                }


                                Toast.makeText(MyPaymentActivity.this, text, Toast.LENGTH_SHORT).show();
                                return true;

                            }
                        })
                        .positiveText("Choose")
                        .show();


            }
        });


    }

    private void fetchMyPayments() {
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("paid", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("unpaid", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
//        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsRVAdapter.notifyDataSetChanged();

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

}