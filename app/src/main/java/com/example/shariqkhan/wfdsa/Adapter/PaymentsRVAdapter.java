package com.example.shariqkhan.wfdsa.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shariqkhan.wfdsa.Dialog.InvoiceDialog;
import com.example.shariqkhan.wfdsa.Dialog.PaymentDialog;
import com.example.shariqkhan.wfdsa.Model.PaymentModel;
import com.example.shariqkhan.wfdsa.MyPaymentActivity;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/26/2017.
 */

public class PaymentsRVAdapter extends RecyclerView.Adapter<PaymentsRVAdapter.MyViewHolder> {
    MyPaymentActivity context;
    ArrayList<PaymentModel> paymentsList;

    public PaymentsRVAdapter(MyPaymentActivity context, ArrayList<PaymentModel> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_payments_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PaymentModel dataModel = paymentsList.get(position);
        final String sendInvoice = dataModel.getInvoice_id();
        Log.e("invoice_id", sendInvoice);

        holder.tvTitle.setText(dataModel.getTitle());
        holder.tvDueDate.setText("Due Date " + dataModel.getDueDate());
        holder.tvPayNow.setText("PAY NOW\n$" + dataModel.getAmount());

        if (dataModel.type.equals("1")) {
            holder.tvDueDate.setText("");
            holder.tvPayNow.setText("PAID");
        } else {
            holder.tvPayNow.setText("UNPAID");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InvoiceDialog dialog = new InvoiceDialog(context, sendInvoice);
                dialog.show();
//                final Dialog dialog = new Dialog(context);
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                dialog.getWindow().setLayout(lp.width, lp.height);
//                dialog.setContentView(R.layout.invoice_dialog);
//                dialog.show();
            }
        });
//
//
//        holder.tvTitle.setText(dataModel.getTitle());
//        holder.tvDueDate.setText("Due Date "+dataModel.getDueDate());
//        holder.tvPayNow.setText("PAY NOW\n$"+dataModel.getAmount());

        holder.tvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tvPayNow.getText().equals("PAID")) {
                    Toast.makeText(context, "YOU ALREADY PAID FOR THIS!", Toast.LENGTH_SHORT).show();
                } else {
                    Dialog d = new PaymentDialog(context);
                    d.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
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
}