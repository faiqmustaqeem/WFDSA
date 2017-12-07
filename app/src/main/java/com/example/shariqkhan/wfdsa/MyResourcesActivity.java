package com.example.shariqkhan.wfdsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.ResourcesRVadapter;
import com.example.shariqkhan.wfdsa.custom.ResourcesGroup;
import com.example.shariqkhan.wfdsa.custom.ResourcesSubItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyResourcesActivity extends AppCompatActivity {
    ImageView ivBack;

    @BindView(R.id.rvResources)
    RecyclerView rvResources;

    public ResourcesRVadapter adapter;
    List<ResourcesSubItems> resourcesSubItemsList = new ArrayList<ResourcesSubItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("MY RESOURCES");

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = rvResources.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        resourcesSubItemsList.add(new ResourcesSubItems("1", "Media Guide"));
        resourcesSubItemsList.add(new ResourcesSubItems("2", "Global Statistics"));
        resourcesSubItemsList.add(new ResourcesSubItems("3", "Industry Message Guidebook"));

        adapter = new ResourcesRVadapter(this, getAllResources());
        rvResources.setLayoutManager(layoutManager);
        rvResources.setAdapter(adapter);

    }

    public List<ResourcesGroup> getAllResources() {
        return Arrays.asList(
                new ResourcesGroup("1", "Advocacy", resourcesSubItemsList),
                new ResourcesGroup("2", "Association Service", resourcesSubItemsList),
                new ResourcesGroup("3", "Global Regulatory", resourcesSubItemsList)
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
}
