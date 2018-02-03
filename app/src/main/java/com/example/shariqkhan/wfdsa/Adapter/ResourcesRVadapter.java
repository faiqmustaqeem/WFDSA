package com.example.shariqkhan.wfdsa.Adapter;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.MainResources;
import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.custom.ResourceGroupViewHolder;
import com.example.shariqkhan.wfdsa.custom.ResourceItemViewHolder;
import com.example.shariqkhan.wfdsa.custom.ResourcesGroup;
import com.example.shariqkhan.wfdsa.custom.ResourcesSubItems;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class ResourcesRVadapter extends ExpandableRecyclerViewAdapter<ResourceGroupViewHolder, ResourceItemViewHolder> {
    Context context;


    public ResourcesRVadapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }


    @Override
    public ResourceGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_group_rv_item, parent, false);
        return new ResourceGroupViewHolder(context, view);
    }

    @Override
    public ResourceItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_sub_item_rv_item, parent, false);
        return new ResourceItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ResourceItemViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final ResourcesSubItems subItem = ((ResourcesGroup) group).getItems().get(childIndex);
        String id = subItem.getId();
        holder.setTitle(id);

        if (subItem.getTitle().contains(".docx")) {
            holder.image.setImageResource(R.drawable.word);
        } else if (subItem.getTitle().contains(".pptx")) {
            holder.image.setImageResource(R.drawable.ppttfile);
        } else if (subItem.getTitle().contains(".pdf")) {
            holder.image.setImageResource(R.drawable.pdf);
        } else if (subItem.getTitle().contains(".xls")) {
            holder.image.setImageResource(R.drawable.excel);
        } else {
            holder.image.setImageResource(R.drawable.fileother);
        }
        GlobalClass.download_file_name = subItem.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Toast.makeText(context, "Downloading!!", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(subItem.getTitle().replaceAll("\\s", ""));
                Log.e("sub", subItem.getTitle());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long refer = manager.enqueue(request);

                GlobalClass.list_downloads.add(refer);



            }
        });


    }

    @Override
    public void onBindGroupViewHolder(ResourceGroupViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setGenreTitle(group);
    }
}