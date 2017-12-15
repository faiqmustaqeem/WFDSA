package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;


import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventGalleryDialog extends Dialog implements View.OnClickListener {
    public Activity act;
    public Dialog d;
    ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
    GridView gridView;
    FloatingActionButton floatingActionButton;
    EventGalleryGVadapter gridAdapter;
    ImageView imageView;


    public EventGalleryDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_gallery_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUI();
        fetchEventImages();
    }

    private void fetchEventImages() {
        imagesList.add(new EventGalleryModel("1", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("2", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
        imagesList.add(new EventGalleryModel("3", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
        imagesList.add(new EventGalleryModel("4", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
        imagesList.add(new EventGalleryModel("5", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("6", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
        imagesList.add(new EventGalleryModel("7", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
        imagesList.add(new EventGalleryModel("8", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
        imagesList.add(new EventGalleryModel("9", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));

        gridAdapter = new EventGalleryGVadapter(act, R.layout.event_gallery_item, imagesList);
        gridView.setAdapter(gridAdapter);
    }

    private void initUI() {
        gridView = (GridView) findViewById(R.id.gridView);
        imageView = findViewById(R.id.close);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddImage);

        if (MainActivity.DECIDER.equals("member")) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSendComment:

                break;
        }
    }
}