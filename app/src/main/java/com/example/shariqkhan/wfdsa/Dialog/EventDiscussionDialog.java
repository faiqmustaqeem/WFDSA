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
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Adapter.DiscussionRVAdapter;
import com.example.shariqkhan.wfdsa.Model.DiscussionModel;
import com.example.shariqkhan.wfdsa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 11/4/2017.
 */

public class EventDiscussionDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ArrayList<DiscussionModel> discussionList = new ArrayList<DiscussionModel>();
    ImageView imageView;

    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.tvSendComment)
    TextView tvSendComment;

    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    DiscussionRVAdapter discussionRVAdapter;


    public EventDiscussionDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_discussion_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUI();
    }

    private void initUI() {
        discussionRVAdapter = new DiscussionRVAdapter(act, discussionList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvComments.setLayoutManager(mAnnouncementLayoutManager);
        rvComments.setItemAnimator(new DefaultItemAnimator());

        tvSendComment.setOnClickListener(this);

        discussionList.add(new DiscussionModel("1", "Person A", "Entrepreneur", "", "I have been waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
        discussionList.add(new DiscussionModel("2", "Person b", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
        discussionList.add(new DiscussionModel("3", "Person c", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
        discussionList.add(new DiscussionModel("4", "Person d", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
        discussionList.add(new DiscussionModel("5", "Person e", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
        discussionList.add(new DiscussionModel("6", "Person f", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));

        rvComments.setAdapter(discussionRVAdapter);

        imageView = findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private boolean validComment() {
        boolean check = false;
        if (etComment.getText().toString().equals("")) {
            check = false;
        } else {
            check = true;
        }
        /*if (etName.getText().toString().equals("")) {
            etName.setError("This field is required");
            check = false;
        } else {
            check = true;
        }*/
        return check;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSendComment:
                if (validComment()) {
                    //get time
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                    //get date
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    discussionList.add(new DiscussionModel("9", "User Name", "DSA Member", etComment.getText().toString(), "com", dateFormat.format(cal.getTime()), sdfTime.format(cal.getTime())));
                    discussionRVAdapter.notifyDataSetChanged();
                    etComment.setText("");
                }
                break;
        }
    }
}