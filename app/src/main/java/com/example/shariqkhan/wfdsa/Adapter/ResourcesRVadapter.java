package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public void onBindGroupViewHolder(ResourceGroupViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setGenreTitle(group);
    }
}