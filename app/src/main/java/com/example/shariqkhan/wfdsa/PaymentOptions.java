package com.example.shariqkhan.wfdsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PaymentOptions extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        imageFilter = (ImageView) toolbar.findViewById(R.id.filter);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] items = new String[]{"My Payments"};
        if (LoginActivity.decider.equals("1")) {
            items = new String[]{"My Payments", "My Invoices"};
        } else if (LoginActivity.decider.equals("2")) {
            items = new String[]{"My Payments"};
        }
        listview = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listview.setAdapter(itemsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent1 = new Intent(PaymentOptions.this, MyPaymentActivity.class);
                        startActivity(intent1);
                        break;

                    case 1:
                        Intent intent2 = new Intent(PaymentOptions.this, MyInvoices.class);
                        startActivity(intent2);
                        break;
                }
            }
        });


    }
}
