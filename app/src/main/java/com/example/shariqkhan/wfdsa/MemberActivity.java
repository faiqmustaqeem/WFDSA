package com.example.shariqkhan.wfdsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.MemberAdapter;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity {

    public RecyclerView listOfMembers;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static String filterableString = "";
    ArrayList<ModelMember> arrayList = new ArrayList<>();
    ArrayList<ModelMember> arrayListSave ;



    Toolbar toolbar;
    ImageView imageFilter;


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

        for (int i = 0; i < 12; i++) {

            ModelMember member = new ModelMember();
            member.setCompanyName("Codian-Soft-");
            member.setCompanyAddress("bbshoppingmall-secondfloor-office3");
            member.setMemberEmail("codianSoft@org");
            member.setMemberName("Shariq Khan");
            member.setMemberFax("03342477874");
            member.setMemberPhone("03342477874");
            member.setMemberWeb("www.codiansoft.com");
            if (i % 2 == 0) {
                member.setCountry("America");
            } else {
                member.setCountry("Pakistan");
            }
            arrayList.add(member);

        }
        Log.e("Real", String.valueOf(arrayList.size()));
        arrayListSave = new ArrayList<>(arrayList);
        Log.e("Clone", String.valueOf(arrayListSave.size()));

        adapter = new MemberAdapter(arrayList);
        listOfMembers.setAdapter(adapter);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageFilter = (ImageView) toolbar.findViewById(R.id.filter);

        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"America", "Pakistan", "All"};
                new LovelyChoiceDialog(MemberActivity.this)
                        .setTopColorRes(R.color.colorPrimary)
                        .setTitle("Select")
                        .setIcon(R.drawable.icons8filter)
                        .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                            @Override
                            public void onItemsSelected(List<Integer> positions, List<String> items) {
                                Toast.makeText(MemberActivity.this, TextUtils.join(" ", items), Toast.LENGTH_SHORT).show();

                                filterableString = TextUtils.join("", items);
                                if (filterableString.equals("America")) {
                                    arrayList.clear();
                                    for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                        ModelMember memberToCheck = new ModelMember();
                                        memberToCheck = arrayListSave.get(counter);
                                        Log.e("MEMBER", String.valueOf(memberToCheck));

                                    if(memberToCheck.Country.equals(filterableString))
                                    {

                                        Log.e("SizeOfAmerica",String.valueOf(arrayList.size()));
                                        arrayList.add(memberToCheck);
                                        Log.e("SizeOfAmerica",String.valueOf(arrayList.size()));

                                    }
                                    }
                                //    Log.e("Jugar",String.valueOf(arrayList.size()));
                                    adapter = new MemberAdapter(arrayList);
                                    listOfMembers.setAdapter(adapter);

                                }else if (filterableString.equals("All"))
                                {

                                    arrayList = new ArrayList<>(arrayListSave);
                                    Log.e("SizeAll",String.valueOf(arrayList.size()));
                                    adapter = new MemberAdapter(arrayListSave);
                                    listOfMembers.setAdapter(adapter);

                                }else if (filterableString.equals("Pakistan"))
                                {
                                    arrayList.clear();
                                    for (int counter = 0; counter < arrayListSave.size(); counter++) {

                                        ModelMember memberToCheck = new ModelMember();
                                        memberToCheck = arrayListSave.get(counter);
                                        Log.e("MEMBER", String.valueOf(memberToCheck));

                                        if(memberToCheck.Country.equals(filterableString))
                                        {

                                            Log.e("SizeOfPak",String.valueOf(arrayList.size()));
                                            arrayList.add(memberToCheck);
                                            Log.e("SizeOfPak",String.valueOf(arrayList.size()));

                                        }
                                    }
                                    Log.e("Jugar",String.valueOf(arrayList.size()));
                                    adapter = new MemberAdapter(arrayList);
                                    listOfMembers.setAdapter(adapter);

                                }


                            }
                        })
                        .setConfirmButtonText("Confirm")
                        .show();
            }
        });


    }
}
