package com.example.shariqkhan.wfdsa.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.shariqkhan.wfdsa.Model.TryModel;
import com.example.shariqkhan.wfdsa.R;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/7/2017.
 */

public class UserGuideAdapter extends RecyclerView.Adapter<UserGuideAdapter.MyViewHolder> {

    public ArrayList<TryModel> arrayList = new ArrayList<>();
    Context context;
    MediaController media;
    int counterOfBind=0;

    public UserGuideAdapter(ArrayList<TryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_user_guide, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.e("onBind", String.valueOf(counterOfBind));
        counterOfBind++;
        final TryModel model = arrayList.get(position);

        holder.textView.setText("HOW TO APPLY FOR MEMBER REGISTRATION "+model.getId());
        //holder.image.setImageResource(R.drawable.shehzadrecovered);

        media = new MediaController(context);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

           //     Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                dialog.setContentView(R.layout.video_dialog_view);
               // Toast.makeText(context, model.getId(), Toast.LENGTH_SHORT).show();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(lp.width, lp.height);

                FullscreenVideoLayout vid = (FullscreenVideoLayout) dialog.findViewById(R.id.videoview);
                media = new MediaController(context);
                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.videoviewtestingvideo);
                try {
                    vid.setVideoURI(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dialog.setCanceledOnTouchOutside(true);


                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView image;
        RelativeLayout relativeLayout;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            relativeLayout = (RelativeLayout) view.findViewById(R.id.mainRel);
            textView = (TextView) view.findViewById(R.id.textOfUser);
            image = (ImageView) view.findViewById(R.id.imageOFplay);
        }
    }
}
