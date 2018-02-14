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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.R;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/6/2017.
 */

public class CEOAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    ArrayList<ModelMember> arrayList = new ArrayList<>();
Context context;
    private int visibleThreshold = 6;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public CEOAdapter(ArrayList<ModelMember> arrayList, Context context, RecyclerView recyclerView) {
        this.arrayList = arrayList;
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
        return arrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemofceo, parent, false);

            vh = new MyHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ModelMember member = arrayList.get(position);

        if (holder instanceof MyHolder) {
            ((MyHolder) holder).country.setText(member.getCountry());
            ((MyHolder) holder).Post.setText(member.getWfdsa_title());


            // holder.name.setText(member.getMemberName());
            ((MyHolder) holder).experties.setText(member.getCompanyName());
            ((MyHolder) holder).fax.setText(member.getMemberFax());
            ((MyHolder) holder).ph.setText(member.getMemberPhone());
            ((MyHolder) holder).email.setText(member.getMemberEmail());
            ((MyHolder) holder).name.setText(member.getFirstname() + " " + member.getLastname());
            ((MyHolder) holder).company_address.setText(member.getCompanyAddress());
            Log.e("flag_pic", member.getFlag_pic());
            ((MyHolder) holder).designation.setText(member.getDesignation());


            if (MainActivity.DECIDER.equals("member")) {
                ((MyHolder) holder).relative.setVisibility(View.VISIBLE);
                Log.e("show", "show");

            } else {
                ((MyHolder) holder).relative.setVisibility(View.GONE);
                Log.e("show", "show");
            }
            try {
                if (!member.getFlag_pic().equals(""))
                    Glide.with(context).load(member.getFlag_pic()).into(((MyHolder) holder).flag);
                if (!member.getUpload_image().equals(""))
                    Glide.with(context).load(member.getUpload_image()).into(((MyHolder) holder).logoFront);
            } catch (Exception e) {
//            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                Log.e("exception", e.getMessage());
                //Picasso.with(context).load(member.getUpload_image()).into(holder.logoFront);
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView experties;
        public TextView Post;
        public TextView designation;
        public TextView country;
        public TextView company_address;
        public TextView fax;
        public TextView email;
        public TextView ph;

        public RelativeLayout relative;

        public View view;
        public ImageView logoFront;

        public ImageView flag;


        public MyHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) view.findViewById(R.id.name);
            experties = (TextView) view.findViewById(R.id.experties);
            Post = (TextView) view.findViewById(R.id.Post);
            country = (TextView) view.findViewById(R.id.country);
            relative = (RelativeLayout) view.findViewById(R.id.showOrHide);
            fax = (TextView) view.findViewById(R.id.faxNumber);
            email = (TextView) view.findViewById(R.id.emailproper);
            ph= (TextView) view.findViewById(R.id.phoneNumber);
            company_address = (TextView)view.findViewById(R.id.company_address);
            logoFront = (ImageView) view.findViewById(R.id.logo);
            flag = (ImageView) view.findViewById(R.id.flag);
            designation=(TextView)view.findViewById(R.id.designation);

        }
    }

}
