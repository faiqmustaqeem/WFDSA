package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.Model.InvoiceModel;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder> {
    ArrayList<InvoiceModel> announcementsList;
    Context context;

    public InvoiceAdapter(Context context, ArrayList<InvoiceModel> announcementsList) {
        this.announcementsList = announcementsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_invoice, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InvoiceModel dataModel = announcementsList.get(position);

      //  holder.tvTotal.setText(dataModel.getTotal());


        holder.tvprice.setText(dataModel.getPrice());
        holder.tvname.setText(dataModel.getName());
        // holder.tvqty.setText(dataModel.getQuantity());

    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.invoicename)
        TextView tvname;


//        @BindView(R.id.invoicequantity)
//        TextView tvqty;


        @BindView(R.id.incvoiceprice)
        TextView tvprice;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
