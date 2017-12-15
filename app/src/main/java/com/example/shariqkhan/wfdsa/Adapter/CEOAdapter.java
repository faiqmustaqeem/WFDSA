package com.example.shariqkhan.wfdsa.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/6/2017.
 */

public class CEOAdapter extends RecyclerView.Adapter<CEOAdapter.MyHolder> {
    ArrayList<ModelMember> arrayList = new ArrayList<>();

    public CEOAdapter(ArrayList<ModelMember> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public CEOAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemofceo, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(CEOAdapter.MyHolder holder, int position) {

        ModelMember member = arrayList.get(position);

        holder.country.setText(member.getCountry());
        // holder.name.setText(member.getMemberName());
        holder.experties.setText("Specialist Consultant");
        if (MainActivity.DECIDER.equals("member")) {
            holder.relative.setVisibility(View.VISIBLE);
        } else {
            holder.relative.setVisibility(View.GONE);
        }


//        holder.holder.Post.setText("Member");logoFront.setImageResource(R.drawable.hr);
//        holder.logoTopLeft.setImageResource(R.drawable.czech);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView experties;
        public TextView Post;
        public TextView country;
        public RelativeLayout relative;

        public View view;
        public ImageView logoFront;

        public ImageView logoTopLeft;


        public MyHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) view.findViewById(R.id.name);
            experties = (TextView) view.findViewById(R.id.experties);
            Post = (TextView) view.findViewById(R.id.Post);
            country = (TextView) view.findViewById(R.id.country);
            relative = (RelativeLayout) view.findViewById(R.id.showOrHide);

            logoFront = (ImageView) view.findViewById(R.id.logo);

            logoTopLeft = (ImageView) view.findViewById(R.id.flag);


        }
    }

}
