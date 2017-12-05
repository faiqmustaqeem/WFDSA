package com.example.shariqkhan.wfdsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shariqkhan.wfdsa.Adapter.MemberAdapter;
import com.example.shariqkhan.wfdsa.Model.ModelMember;

import java.util.ArrayList;

public class MemberActivity extends AppCompatActivity {

    public RecyclerView listOfMembers;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMember> arrayList = new ArrayList<>();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        listOfMembers = (RecyclerView) findViewById(R.id.memberList);

        layoutManager = new LinearLayoutManager(this);
        listOfMembers.setLayoutManager(layoutManager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        for (int i = 0; i < 12; i++) {

            ModelMember member = new ModelMember();
            member.setCompanyName("Codian-Soft-");
            member.setCompanyAddress("bbshoppingmall-secondfloor-office3");
            member.setMemberEmail("codianSoft@org");
            member.setMemberName("Shariq Khan");
            member.setMemberFax("03342477874");
            member.setMemberPhone("03342477874");
            member.setMemberWeb("www.codiansoft.com");
            arrayList.add(member);

        }

        adapter = new MemberAdapter(arrayList);
        listOfMembers.setAdapter(adapter);
    }
}
