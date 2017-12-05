package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView image;
    ArrayList<PaymentModel> paymentsList = new ArrayList<PaymentModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
        ButterKnife.bind(this);


        paymentsRVAdapter = new PaymentsRVAdapter(this, paymentsList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPayments.setLayoutManager(mAnnouncementLayoutManager);
        rvPayments.setItemAnimator(new DefaultItemAnimator());
        rvPayments.setAdapter(paymentsRVAdapter);

        fetchMyPayments();
        image = (ImageView)findViewById(R.id.ivBack);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           finish();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("My Payments");
    }

    private void fetchMyPayments() {
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
        paymentsList.add(new PaymentModel("1", "12/12/2017", "Annual Payment", "5000"));
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