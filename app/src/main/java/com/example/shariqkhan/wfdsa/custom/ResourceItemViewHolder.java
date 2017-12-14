package com.example.shariqkhan.wfdsa.custom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class ResourceItemViewHolder extends ChildViewHolder {

    private TextView tvTitle;
    public ImageView image;

    public ResourceItemViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.list_item_artist_name);
        image = (ImageView) itemView.findViewById(R.id.leftimage);
    }

    public void setTitle(String name) {
        tvTitle.setText(name);
    }
}