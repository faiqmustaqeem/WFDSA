package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.Model.AttendeesModel;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class AttendeesRVAdapter extends RecyclerView.Adapter<AttendeesRVAdapter.MyViewHolder> {
    ArrayList<AttendeesModel> attendeesList;
    Context context;
    int lastPosition = -1;

    public AttendeesRVAdapter(Context context, ArrayList<AttendeesModel> attendeesList) {
        this.attendeesList = attendeesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendees_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AttendeesModel dataModel = attendeesList.get(position);
        holder.tvName.setText(dataModel.getName());
        // holder.tvDescription.setText(dataModel.getDescription());

            holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_profile_pic));


        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.rv_up_from_bottom
                        : R.anim.rv_down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return attendeesList.size();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivImage)
        ImageView ivImage;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
