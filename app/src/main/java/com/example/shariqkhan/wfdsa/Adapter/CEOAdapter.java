package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.MainActivity;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.R;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 12/6/2017.
 */

public class CEOAdapter extends RecyclerView.Adapter<CEOAdapter.MyHolder> {
    ArrayList<ModelMember> arrayList = new ArrayList<>();
Context context;
    public CEOAdapter(ArrayList<ModelMember> arrayList, Context context) {
        this.arrayList = arrayList;
    this.context = context;
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
        holder.Post.setText(member.getWfdsa_title());


        // holder.name.setText(member.getMemberName());
        holder.experties.setText(member.getCompanyName());
        holder.fax.setText(member.getMemberFax());
        holder.ph.setText(member.getMemberPhone());
        holder.email.setText(member.getMemberEmail());
        holder.name.setText(member.getFirstname()+" "+member.getLastname());
        holder.company_address.setText(member.getCompanyAddress());
        Log.e("flag_pic" , member.getFlag_pic());
        holder.designation.setText(member.getDesignation());


       if (MainActivity.DECIDER.equals("member"))
        {
            holder.relative.setVisibility(View.VISIBLE);
        } else {
            holder.relative.setVisibility(View.GONE);
        }
        try{
            if(!member.getFlag_pic().equals(""))
            Picasso.with(context).load(member.getFlag_pic()).into(holder.flag);
            if(!member.getUpload_image().equals(""))
            Picasso.with(context).load(member.getUpload_image()).into(holder.logoFront);    
        }catch (Exception e)
        {
//            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            Log.e("exception" , e.getMessage());
            //Picasso.with(context).load(member.getUpload_image()).into(holder.logoFront);
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
