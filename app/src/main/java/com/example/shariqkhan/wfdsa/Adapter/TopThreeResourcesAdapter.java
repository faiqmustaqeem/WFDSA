package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.MainResources;
import com.example.shariqkhan.wfdsa.Model.TopThreeModel;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodianSoft on 10/02/2018.
 */

public class TopThreeResourcesAdapter extends RecyclerView.Adapter<TopThreeResourcesAdapter.MyViewHolder> {
    List<TopThreeModel> list = new ArrayList<>();
    Context context;

    public TopThreeResourcesAdapter(List<TopThreeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopThreeResourcesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopThreeResourcesAdapter.MyViewHolder holder, int position) {
        TopThreeModel model = list.get(position);

        holder.name.setText(model.getName());

        if (model.getPath().contains(".docx")) {
            holder.image.setImageResource(R.drawable.word);
        } else if (model.getPath().contains(".pptx")) {
            holder.image.setImageResource(R.drawable.ppttfile);
        } else if (model.getPath().contains(".pdf")) {
            holder.image.setImageResource(R.drawable.pdf);
        } else if (model.getPath().contains(".xls") || model.getPath().contains(".csv")) {
            holder.image.setImageResource(R.drawable.excel);
        } else {
            holder.image.setImageResource(R.drawable.fileother);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MainResources.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_item_artist_name);
            image = itemView.findViewById(R.id.leftimage);
        }
    }
}
