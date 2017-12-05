package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.AttendeesRVAdapter;
import com.example.shariqkhan.wfdsa.Model.AttendeesModel;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventAttendeesDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ArrayList<AttendeesModel> attendeesList = new ArrayList<AttendeesModel>();

    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    @BindView(R.id.rvAttendees)
    RecyclerView rvAttendees;
    AttendeesRVAdapter attendeesRVAdapter;

    public EventAttendeesDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_attendees_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUI();
    }

    private void initUI() {
        attendeesRVAdapter = new AttendeesRVAdapter(act, attendeesList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvAttendees.setLayoutManager(mAnnouncementLayoutManager);
        rvAttendees.setItemAnimator(new DefaultItemAnimator());

        attendeesList.add(new AttendeesModel("1", "Person A", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("2", "Person b", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("3", "Person c", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("4", "Person d", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("5", "Person e", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("6", "Person f", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("7", "Person g", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));
        attendeesList.add(new AttendeesModel("8", "Person h", "Entrepreneur", "https://www.ft.com/__origami/service/image/v2/images/raw/fthead:leslie-hook?source=next&fit=scale-down&width=150&compression=best&quality=highest&dpr=2"));

        rvAttendees.setAdapter(attendeesRVAdapter);
    }

    @OnClick({R.id.tvCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }
}