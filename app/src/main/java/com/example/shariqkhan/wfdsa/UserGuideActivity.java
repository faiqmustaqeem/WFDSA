package com.example.shariqkhan.wfdsa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.VideoView;

import com.example.shariqkhan.wfdsa.Adapter.UserGuideAdapter;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.Model.TryModel;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class UserGuideActivity extends AppCompatActivity {


    public RecyclerView listOfMembers;
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TryModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);
        layoutManager = new LinearLayoutManager(this);
        listOfMembers.setLayoutManager(layoutManager);


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

        for (int i = 0; i < 10; i++) {
           TryModel model = new TryModel();
           model.setId(String.valueOf(i));
           model.setText("HOW TO BECOME A MEMBER");
            arrayList.add(model);
        }

        adapter = new UserGuideAdapter(arrayList, this);
        listOfMembers.setAdapter(adapter);


    }
}
