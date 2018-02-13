package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codiansoft on 10/11/2017.
 */

public class AnnouncementsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    ArrayList<AnnouncementsModel> announcementsList;
    Context context;
    private int visibleThreshold = 6;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public AnnouncementsRVAdapter(Context context, ArrayList<AnnouncementsModel> announcementsList , RecyclerView recyclerView) {
        this.announcementsList = announcementsList;
        this.context = context;


        RecyclerView.LayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager1);
//                    rvAnnouncements.setItemAnimator(new DefaultItemAnimator());
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });

    }
    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return announcementsList.get(position)==null? VIEW_TYPE_LOADING: VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType==VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.announcements_rv_item, parent, false);

            vh = new MyViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AnnouncementsModel dataModel = announcementsList.get(position);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).tvTitle.setText(dataModel.getTitle());
            ((MyViewHolder)holder).tvDate.setText(dataModel.getDate());
            ((MyViewHolder)holder).tvAnnouncementDescription.setText(dataModel.getDescription());

            try {
                if (dataModel.getImage().endsWith(".jpg") || dataModel.getImage().endsWith(".png")) {
                    Log.e("imageUrl", dataModel.getImage());
                    Picasso.with(context).load(dataModel.getImage()).into(((MyViewHolder) holder).ivImage);
                } else {
                    ((MyViewHolder) holder).ivImage.setVisibility(View.GONE);
                }
            } catch (IllegalArgumentException ie) {
                ((MyViewHolder)holder).ivImage.setVisibility(View.GONE);
            }
        }
        else {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
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
