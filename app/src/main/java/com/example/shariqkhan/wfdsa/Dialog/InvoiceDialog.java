package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


//import com.example.shariqkhan.wfdsa.Adapter.DiscussionRVAdapter;
import com.example.shariqkhan.wfdsa.Adapter.InvoiceAdapter;
import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.LeaderShipActivity;
import com.example.shariqkhan.wfdsa.Model.DiscussionModel;
import com.example.shariqkhan.wfdsa.Model.InvoiceModel;
import com.example.shariqkhan.wfdsa.Model.LeaderShipModel;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.example.shariqkhan.wfdsa.R;

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
 * Created by Codiansoft on 11/4/2017.
 */

public class InvoiceDialog extends Dialog {
    public Activity act;
    public Dialog d;
    ArrayList<InvoiceModel> discussionList = new ArrayList<InvoiceModel>();
    ImageView imageView;
    String invoice_id;
    TextView Title;
    TextView Duedate;
    ProgressDialog progressDialog;
    TextView totalAmount;

    public String URL = GlobalClass.base_url+"wfdsa/apis/Invoice/InvoiceDetail?";


    @BindView(R.id.rvAttendees)
    RecyclerView rvComments;
    InvoiceAdapter discussionRVAdapter;


    public InvoiceDialog(Activity a, String invoice_id) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        this.invoice_id = invoice_id;
        URL = URL + "invoice_id=" + this.invoice_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_invoice_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        imageView = findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Title = findViewById(R.id.invoiceTitle);
        Duedate = findViewById(R.id.invoiceDate);
totalAmount = findViewById(R.id.totlAmount);
//        initUI();
        Task task = new Task();
        task.execute();

    }

    private class Task extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {

            String url = URL;

            Log.e("url", url);

            String response = getHttpData.getData(url);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Res", s);
            try {
                JSONObject jsonObject = new JSONObject(s);


                JSONObject resultObj = jsonObject.getJSONObject("result");
                String getstatus = resultObj.getString("status");


                if (getstatus.equals("success")) {
                  JSONObject object = resultObj.getJSONObject("data");
                    totalAmount.setText(object.getString("amount"));
                    Title.setText(object.getString("title"));
                    Duedate.setText(object.getString("name"));

                    JSONArray rolesArray = object.getJSONArray("item");


                    for (int i = 0; i < rolesArray.length(); i++) {
                        InvoiceModel model = new InvoiceModel();
                        JSONObject obj = rolesArray.getJSONObject(i);

                        model.setPrice(obj.getString("amount"));
                        model.setName(obj.getString("name"));
                        model.setQuantity("5");
                        discussionList.add(model);
                    }
                    discussionRVAdapter = new InvoiceAdapter(act, discussionList);
                    RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
                    rvComments.setLayoutManager(mAnnouncementLayoutManager);
                    rvComments.setItemAnimator(new DefaultItemAnimator());
                    rvComments.setAdapter(discussionRVAdapter);

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

    private void initUI() {
        discussionRVAdapter = new InvoiceAdapter(act, discussionList);
        RecyclerView.LayoutManager mAnnouncementLayoutManager = new LinearLayoutManager(act);
        rvComments.setLayoutManager(mAnnouncementLayoutManager);
        rvComments.setItemAnimator(new DefaultItemAnimator());


//        discussionList.add(new DiscussionModel("1", "Person A", "Entrepreneur", "", "I have been waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
//        discussionList.add(new DiscussionModel("2", "Person b", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
//        discussionList.add(new DiscussionModel("3", "Person c", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
//        discussionList.add(new DiscussionModel("4", "Person d", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
//        discussionList.add(new DiscussionModel("5", "Person e", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));
//        discussionList.add(new DiscussionModel("6", "Person f", "Entrepreneur", "", "Me was waiting for this to happen for a long time", "31-11-2017", "6:30AM"));

        rvComments.setAdapter(discussionRVAdapter);

    }


}