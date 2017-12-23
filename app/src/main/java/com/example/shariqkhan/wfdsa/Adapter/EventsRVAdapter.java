package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.AnnouncementsActivity;
import com.example.shariqkhan.wfdsa.MainActivity;
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

        ImageView lock;

        View itemView;

        public MyViewHolder(View view) {
            super(view);


            itemView = view;
            ButterKnife.bind(this, view);
            lock = (ImageView) itemView.findViewById(R.id.lock);

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
        // holder.tvDayTime.setText(dataModel.getDay() + ", @" + dataModel.getTime());
        holder.tvCityCountry.setText(dataModel.getVenueCity());
        if (holder.tvCityCountry.getText().equals(""))
            holder.tvCityCountry.setText("Paris");
      //  holder.tvDay.setText(dataModel.getDay());
        //holder.tvMonth.setText(dataModel.getMonth());

        if (!MainActivity.DECIDER.equals("member"))
        {
           if (dataModel.getPersonal().equals("member"))
            {
             holder.lock.setVisibility(View.VISIBLE);
            }
        }else
            {
                holder.lock.setVisibility(View.GONE);
            }

        // holder.tvYear.setText(dataModel.getYear());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( context, AnnouncementsActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return eventsModelArrayList.size();
    }
}
