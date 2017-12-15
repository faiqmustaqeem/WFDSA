package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventPollsDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ImageView imageView;

    @BindView(R.id.tvSubmit)
    TextView tvSubmit;


    public EventPollsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_polls_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUI();
    }

    private void initUI() {
        imageView = findViewById(R.id.close);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @OnClick({R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                dismiss();
                break;
        }
    }
}