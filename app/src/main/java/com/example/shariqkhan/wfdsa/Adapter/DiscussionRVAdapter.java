//package com.example.shariqkhan.wfdsa.Adapter;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.shariqkhan.wfdsa.Model.DiscussionModel;
//import com.example.shariqkhan.wfdsa.Model.MessageModel;
//import com.example.shariqkhan.wfdsa.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Codiansoft on 11/4/2017.
// */
//
//public class DiscussionRVAdapter extends RecyclerView.Adapter<DiscussionRVAdapter.MyViewHolder> {
//    ArrayList<MessageModel> discussionList = new ArrayList<>();
//    Context context;
//    int lastPosition = -1;
//
//    public DiscussionRVAdapter(Context context, ArrayList<MessageModel> discussionList) {
//        this.discussionList = discussionList;
//        this.context = context;
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tvSenderName)
//        TextView tvSenderName;
//        @BindView(R.id.tvDescription)
//        TextView tvDescription;
//        @BindView(R.id.tvDate)
//        TextView tvDate;
//        @BindView(R.id.tvTime)
//        TextView tvTime;
//        @BindView(R.id.tvComment)
//        TextView tvComment;
//        @BindView(R.id.ivSenderImage)
//        ImageView ivSenderImage;
//
//        public MyViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_rv_item, parent, false);
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        MessageModel dataModel = discussionList.get(position);
//        holder.tvSenderName.setText(dataModel.getName());
//        holder.tvDate.setText(dataModel.getDate());
//        holder.tvDescription.setText(dataModel.getPost());
//        holder.tvTime.setText(dataModel.getTime());
//        holder.tvComment.setText(dataModel.getMessage());
//
//            holder.ivSenderImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_profile_pic));
//
////
////        Animation animation = AnimationUtils.loadAnimation(context,
////                (position > lastPosition) ? R.anim.rv_up_from_bottom
////                        : R.anim.rv_down_from_top);
////        holder.itemView.startAnimation(animation);
////        lastPosition = position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return discussionList.size();
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(MyViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.itemView.clearAnimation();
//    }
//}
