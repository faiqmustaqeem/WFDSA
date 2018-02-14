package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ShariqKhan on 12/5/2017.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyHolder> {



    ArrayList<ModelMember> arrayList = new ArrayList<>();
    Context context;


    public MemberAdapter(ArrayList<ModelMember> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemofmember, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ModelMember member = arrayList.get(position);

        holder.memberWeb.setText(member.getMemberWeb());
        holder.memberName.setText(member.getMemberName());
        holder.memberEmail.setText(member.getMemberEmail());
        holder.memberFax.setText(member.getMemberFax());
        holder.memberPh.setText(member.getMemberPhone());
        holder.companyAddress.setText(member.getCompanyAddress());
        holder.compnayName.setText(member.getCompanyName());
        holder.countryName.setText(member.getCountry());

        Glide.with(context).load(member.getCompany_logo()).into(holder.logoFront);
        Glide.with(context).load(member.getFlag_pic()).into(holder.logoTopLeft);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        public TextView compnayName;
        public TextView companyAddress;
        public TextView memberName;
        public TextView memberPh;
        public TextView memberFax;
        public TextView memberEmail;
        public TextView memberWeb;
        public View view;
        public ImageView logoFront;
        public TextView countryName;
        public ImageView logoTopLeft;


        public MyHolder(View itemView) {
            super(itemView);
            view = itemView;
            compnayName = (TextView) view.findViewById(R.id.company_name);
            companyAddress = (TextView) view.findViewById(R.id.company_address);
            memberName = (TextView) view.findViewById(R.id.Name);
            memberPh = (TextView) view.findViewById(R.id.Phone);
            memberFax = (TextView) view.findViewById(R.id.Fax);
            memberEmail = (TextView) view.findViewById(R.id.Email);
            memberWeb = (TextView) view.findViewById(R.id.Web);
            countryName = (TextView) view.findViewById(R.id.countruname);
            logoFront = (ImageView) view.findViewById(R.id.logo);

            logoTopLeft = (ImageView) view.findViewById(R.id.flag);


        }
    }

}
