package com.example.shariqkhan.wfdsa.custom;

import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shariqkhan.wfdsa.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class ResourceGroupViewHolder extends GroupViewHolder {
    Context context;
    private TextView tvTitle;
    private ImageView arrow;
    private FrameLayout flResourceGroup;

    public ResourceGroupViewHolder(Context context, View itemView) {
        super(itemView);
        flResourceGroup = (FrameLayout) itemView.findViewById(R.id.flResourceGroup);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        arrow = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
        this.context = context;
    }

    public void setGenreTitle(ExpandableGroup resourceGroup) {
        if (resourceGroup instanceof ResourcesGroup) {
            tvTitle.setText(resourceGroup.getTitle());
        }
    }

    @Override
    public void expand() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            flResourceGroup.setBackgroundColor(R.color.colorPrimary);
        } else {
            flResourceGroup.setBackgroundColor(R.color.colorPrimary);
        }
        animateExpand();
    }

    @Override
    public void collapse() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            flResourceGroup.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rectangle));
        } else {
            flResourceGroup.setBackground(context.getResources().getDrawable(R.drawable.rectangle));
        }
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}