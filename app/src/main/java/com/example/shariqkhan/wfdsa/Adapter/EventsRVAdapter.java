package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.Model.EventsModel;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.MyViewHolder> {
    ArrayList<EventsModel> eventsModelArrayList;
    Context context;

    public EventsRVAdapter(Context context, ArrayList<EventsModel> eventsModelArrayList) {
        this.eventsModelArrayList = eventsModelArrayList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDayTime)
        TextView tvDayTime;
        @BindView(R.id.tvCityCountry)
        TextView tvCityCountry;
        @BindView(R.id.tvDay)
        TextView tvDay;
        @BindView(R.id.tvMonth)
        TextView tvMonth;
        @BindView(R.id.tvYear)
        TextView tvYear;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventsModel dataModel = eventsModelArrayList.get(position);
        holder.tvTitle.setText(dataModel.getEventTitle());
        holder.tvDayTime.setText(dataModel.getDay() + ", @" + dataModel.getTime());
        holder.tvCityCountry.setText(dataModel.getVenueCity() + ", " + dataModel.getVenueCountry());
        holder.tvDay.setText(dataModel.getDay());
        holder.tvMonth.setText(dataModel.getMonth());
        holder.tvYear.setText(dataModel.getYear());
    }

    @Override
    public int getItemCount() {
        return eventsModelArrayList.size();
    }
}
