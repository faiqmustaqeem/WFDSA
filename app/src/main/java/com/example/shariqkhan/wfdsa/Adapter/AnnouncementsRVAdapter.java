package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.Model.AnnouncementsModel;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class AnnouncementsRVAdapter extends RecyclerView.Adapter<AnnouncementsRVAdapter.MyViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    ArrayList<AnnouncementsModel> announcementsList;
    Context context;

    public AnnouncementsRVAdapter(Context context, ArrayList<AnnouncementsModel> announcementsList) {
        this.announcementsList = announcementsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcements_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AnnouncementsModel dataModel = announcementsList.get(position);
        holder.tvTitle.setText(dataModel.getTitle());
        holder.tvDate.setText(dataModel.getDate());
        holder.tvAnnouncementDescription.setText(dataModel.getDescription());
        try {
            Log.e("imageUrl", dataModel.getImage());
            Picasso.with(context).load(dataModel.getImage()).into(holder.ivImage);
        } catch (IllegalArgumentException ie) {
            holder.ivImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.ivImage)
        ImageView ivImage;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvAnnouncementDescription)
        TextView tvAnnouncementDescription;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
