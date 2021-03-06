package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.shariqkhan.wfdsa.Model.ModelMember;
import com.example.shariqkhan.wfdsa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShariqKhan on 12/5/2017.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyHolder>  implements Filterable {



    List<ModelMember> arrayList;
    List<ModelMember> filteredlistMembers;
    Context context;


    public MemberAdapter(ArrayList<ModelMember> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        filteredlistMembers=arrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemofmember, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        ModelMember member = filteredlistMembers.get(position);

        holder.memberWeb.setText(member.getMemberWeb());
        holder.memberName.setText(member.getMemberName());
        holder.memberEmail.setText(member.getMemberEmail());
        holder.memberFax.setText(member.getMemberFax());
        holder.memberPh.setText(member.getMemberPhone());
        holder.companyAddress.setText(member.getCompanyAddress());
        holder.compnayName.setText(member.getCompanyName());
        holder.countryName.setText(member.getCountry());

        Glide.with(context).
                load(member.getCompany_logo())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).
                into(holder.logoFront);

        Glide.with(context).load(member.getFlag_pic()).into(holder.logoTopLeft);


    }

    @Override
    public int getItemCount() {
        return filteredlistMembers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredlistMembers = arrayList;
                } else {
                    List<ModelMember> filteredList = new ArrayList<>();
                    for (ModelMember row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (charString.equals("All")) {
                            filteredList.add(row);
                        }
                        else if(charString.toLowerCase().equals(row.getRegion().toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }

                    filteredlistMembers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlistMembers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredlistMembers = (ArrayList<ModelMember>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
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
        public ProgressBar progressBar;


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
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        }
    }

}
