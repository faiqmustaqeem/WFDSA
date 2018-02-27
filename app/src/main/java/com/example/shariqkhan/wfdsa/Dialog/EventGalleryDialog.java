package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Adapter.EventGalleryGVadapter;
import com.example.shariqkhan.wfdsa.Adapter.InvoiceAdapter;
import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.Model.InvoiceModel;
import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.SelectedEventActivity;
import com.google.android.gms.gcm.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static int MULTI_SELECT = 1;
    public Activity act;
    public Dialog d;
    ArrayList<EventGalleryModel> imagesList = new ArrayList<EventGalleryModel>();
    GridView gridView;
    FloatingActionButton floatingActionButton;
    EventGalleryGVadapter gridAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    String id;
    String URL = GlobalClass.base_url+"wfdsa/apis/Event/Gallery?";

    public EventGalleryDialog(Activity a, String id) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        this.id = id;
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
    //    fetchEventImages();


        Task task = new Task();
        task.execute();


    }

    private void initUI() {
        gridView = (GridView) findViewById(R.id.gridView);
        imageView = findViewById(R.id.close);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddImage);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**These following line is the important one!
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                act.startActivityForResult(Intent.createChooser(intent, "Select Pictures"), MULTI_SELECT);
            }
        });


//        if (MainActivity.DECIDER.equals("member")) {
//            floatingActionButton.setVisibility(View.VISIBLE);
//        } else {
//            floatingActionButton.setVisibility(View.INVISIBLE);
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

//
//
//    private void fetchEventImages() {
//        imagesList.add(new EventGalleryModel("1", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("2", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//        imagesList.add(new EventGalleryModel("3", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//        imagesList.add(new EventGalleryModel("4", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//        imagesList.add(new EventGalleryModel("5", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("6", "https://kawarthanow.com/wp-content/uploads/2016/03/pdi-meeting-mar4-01.jpg"));
//        imagesList.add(new EventGalleryModel("7", "https://www.lexisnexis.com/images/lncareers/img-professional-group.jpg"));
//        imagesList.add(new EventGalleryModel("8", "https://www.iaca.int/images/news/2013/Expert_Group_Meeting_I.jpg"));
//        imagesList.add(new EventGalleryModel("9", "http://birnbeckregenerationtrust.org.uk/images/web/publicmeetinggroup.jpg"));
//
//        gridAdapter = new EventGalleryGVadapter(act, R.layout.event_gallery_item, imagesList);
//        gridView.setAdapter(gridAdapter);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSendComment:
                break;
        }
    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL + "event_id=" + id;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                Log.e("Res", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);


                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");


                    if (getstatus.equals("success")) {
                        JSONArray jsonArray = resultObj.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);

                            boolean isRegistered = GlobalClass.isAlreadyRegistered;
                            boolean isCheckedIn = SelectedEventActivity.isCheckedIn;
                            String permission = obj.getString("permission");

                            if (permission.equals("1")) {
                                if (isRegistered && isCheckedIn) {
                                    EventGalleryModel model = new EventGalleryModel(obj.getString("event_id"), obj.getString("gallery_images"));
                                    imagesList.add(model);
                                }
                            } else if (permission.equals("0")) {
                                if (isRegistered) {
                                    EventGalleryModel model = new EventGalleryModel(obj.getString("event_id"), obj.getString("gallery_images"));
                                    imagesList.add(model);
                                }
                            }




                        }

                        gridAdapter = new EventGalleryGVadapter(act, R.layout.event_gallery_item, imagesList);
                        gridView.setAdapter(gridAdapter);
//                    discussionRVAdapter = new InvoiceAdapter(act, discussionList);
//                    RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
//                    rvComments.setLayoutManager(mAnnouncementLayoutManager);
//                    rvComments.setItemAnimator(new DefaultItemAnimator());
//                    rvComments.setAdapter(discussionRVAdapter);

                    }
                    progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(act , "You are not connected to internet !" ,Toast.LENGTH_LONG).show();
            }



        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(act);
            progressDialog.setTitle("Loading ");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }


}