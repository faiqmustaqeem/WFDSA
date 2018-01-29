package com.example.shariqkhan.wfdsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shariqkhan.wfdsa.GlobalClass;
import com.example.shariqkhan.wfdsa.Model.MainResourceModel;
import com.example.shariqkhan.wfdsa.MyResourcesActivity;
import com.example.shariqkhan.wfdsa.R;

import java.util.ArrayList;

/**
 * Created by Codiansoft on 1/13/2018.
 */

public class MainResourceAdapter extends RecyclerView.Adapter<MainResourceAdapter.MyViewHolder> {

    ArrayList<MainResourceModel> arrayList = new ArrayList<>();
    Context con;

    public MainResourceAdapter(ArrayList<MainResourceModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.con = context;

    }

    @Override
    public MainResourceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainResourceAdapter.MyViewHolder holder, int position) {

        final MainResourceModel model = arrayList.get(position);

        holder.list_item.setText(model.getTitle());
//        if (holder.list_item.getText().toString().contains("Word"))
//        {
//            holder.leftImage.setImageResource(R.drawable.word);
//        }else if(holder.list_item.getText().toString().contains("Pdf"))
//        {
//            holder.leftImage.setImageResource(R.drawable.pdf);
//        }else{
//            holder.leftImage.setImageResource(R.drawable.excel);
//        }

    holder.VIEW.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (model.getResource_memeber().equals("Mmeber"))
            {
                Toast.makeText(con, "Members Access Only!", Toast.LENGTH_SHORT).show();
            }else
                {

                    Intent intent = new Intent(con, MyResourcesActivity.class);
                    GlobalClass.selected_resource=model.getTitle();
                    intent.putExtra("RoleName",model.getResource_id());
                    con.startActivity(intent);
                }
        }
    });

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView list_item;
        public ImageView leftImage;
        public View VIEW;
        public MyViewHolder(View itemView) {
            super(itemView);
        VIEW = itemView;
            list_item = (TextView) VIEW.findViewById(R.id.list_item_artist_name);
            leftImage = (ImageView) VIEW.findViewById(R.id.leftimage);

        }
    }

}
