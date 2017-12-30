package com.example.shariqkhan.wfdsa.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.R;
import com.example.shariqkhan.wfdsa.custom.ResourceGroupViewHolder;
import com.example.shariqkhan.wfdsa.custom.ResourceItemViewHolder;
import com.example.shariqkhan.wfdsa.custom.ResourcesGroup;
import com.example.shariqkhan.wfdsa.custom.ResourcesSubItems;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

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

        holder.setTitle(subItem.getTitle());
        String id = subItem.getId();
        if (subItem.getTitle().contains(".docx")) {
            holder.image.setImageResource(R.drawable.word);
        } else if (subItem.getTitle().contains(".pptx")) {
            holder.image.setImageResource(R.drawable.ppttfile);
        } else if (subItem.getTitle().contains(".pdf")) {
            holder.image.setImageResource(R.drawable.pdf);
        }else if (subItem.getTitle().contains(".xls")) {
            holder.image.setImageResource(R.drawable.excel);
        }
        else {
            holder.image.setImageResource(R.drawable.fileother);
        }
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Toast.makeText(context, "Downloading!!", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(subItem.getTitle());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long refer = manager.enqueue(request);
            }
        });


    }

    @Override
    public void onBindGroupViewHolder(ResourceGroupViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setGenreTitle(group);
    }
}