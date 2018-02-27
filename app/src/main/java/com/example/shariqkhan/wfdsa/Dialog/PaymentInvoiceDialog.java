package com.example.shariqkhan.wfdsa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.Adapter.InvoiceAdapter;
import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.Helper.getHttpData;
import com.example.shariqkhan.wfdsa.Model.InvoiceModel;
import com.example.shariqkhan.wfdsa.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentInvoiceDialog extends Dialog {
    public Activity act;
    public Dialog d;
    public String URL = GlobalClass.base_url + "wfdsa/apis/Invoice/PaymentDetail?";

    ImageView imageView;
    String payment_id;
    TextView Title;

    ProgressDialog progressDialog;
    TextView totalAmount ;


    TextView event_name;


    TextView event_payment_date;

    public PaymentInvoiceDialog(Activity a, String payment_id) {
        super(a);
        // TODO Auto-generated constructor stub
        this.act = a;
        this.payment_id = payment_id;
        URL = URL + "payment_id=" + this.payment_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment_invoice_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        event_name=(TextView)findViewById(R.id.event_name);
        event_payment_date=(TextView)findViewById(R.id.payment_date1);

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

        totalAmount = findViewById(R.id.totlAmount);
//        initUI();
        Task task = new Task();
        task.execute();

    }

    private void initUI() {

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
            if(s!=null)
            {
                Log.e("Res", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);


                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    String getstatus = resultObj.getString("status");


                    if (getstatus.equals("success")) {
                        JSONArray jsonArray = resultObj.getJSONArray("data");
                        JSONObject object=jsonArray.getJSONObject(0);


                        // Title.setText("Event Payment");
                        totalAmount.setText(object.getString("payment_amount"));
                        event_name.setText(object.getString("title"));
                        event_payment_date.setText(object.getString("payment_date"));
                    }
                    progressDialog.dismiss();

//                } else {
//                    Toast.makeText(LeaderShipActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

                } catch (JSONException e) {
                    if(e.getMessage()!=null)
                        Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
            else {
                Toast.makeText(act , "you are not connected to internet !" , Toast.LENGTH_LONG).show();
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


}
