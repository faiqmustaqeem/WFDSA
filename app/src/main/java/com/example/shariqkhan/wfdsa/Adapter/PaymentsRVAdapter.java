package com.example.shariqkhan.wfdsa.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Dialog.PaymentDialog;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/26/2017.
 */

public class PaymentsRVAdapter extends RecyclerView.Adapter<PaymentsRVAdapter.MyViewHolder> {
    Context context;
    ArrayList<PaymentModel> paymentsList;

    public PaymentsRVAdapter(Context context, ArrayList<PaymentModel> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDueDate)
        TextView tvDueDate;
        @BindView(R.id.tvPayNow)
        TextView tvPayNow;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_payments_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaymentModel dataModel = paymentsList.get(position);
        holder.tvTitle.setText(dataModel.getTitle());
        holder.tvDueDate.setText("Due Date "+dataModel.getDueDate());
        holder.tvPayNow.setText("PAY NOW\n$"+dataModel.getAmount());

        holder.tvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new PaymentDialog(context);
                d.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }
}