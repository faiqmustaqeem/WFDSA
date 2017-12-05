package com.example.shariqkhan.wfdsa.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.Model.EventGalleryModel;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class EventGalleryGVadapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<EventGalleryModel> data = new ArrayList<EventGalleryModel>();

    public EventGalleryGVadapter(Context context, int layoutResourceId, ArrayList<EventGalleryModel> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        EventGalleryModel item = data.get(position);
        Picasso.with(context).load(item.getImageURL()).into(holder.image);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}