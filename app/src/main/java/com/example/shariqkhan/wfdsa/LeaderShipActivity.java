package com.example.shariqkhan.wfdsa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class LeaderShipActivity extends AppCompatActivity {
    public ListView listOfMembers;

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership);

        listOfMembers = (ListView) findViewById(R.id.memberList);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final String Array[] = {"CEO Council", "Operating Group", "Board of Delegates", "Advisory Council"};

        listOfMembers.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Array));
        listOfMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String get = parent.getItemAtPosition(position).toString();
                if (get.equals(Array[0])) {
                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
                } else if (get.equals(Array[1])) {
                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
                } else if (get.equals(Array[2])) {
                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();
                } else if (get.equals(Array[3])) {
                    Toast.makeText(LeaderShipActivity.this, get, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
