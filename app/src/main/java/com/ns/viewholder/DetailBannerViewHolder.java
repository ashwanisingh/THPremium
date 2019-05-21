package com.ns.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ns.thpremium.R;


/**
 * Created by ashwanisingh on 25/09/18.
 */

public class DetailBannerViewHolder extends RecyclerView.ViewHolder {

    public  ImageView articleTypeimageView;
    public ImageView imageView;
    public TextView mTitleTV;
    public TextView captionText;
    public TextView timeTxt;
    public View shadowOverlay;

    public DetailBannerViewHolder(View itemView) {
        super(itemView);
        articleTypeimageView = itemView.findViewById(R.id.articleTypeimageView);
        imageView = itemView.findViewById(R.id.imageView);
        mTitleTV = itemView.findViewById(R.id.title);
        captionText = itemView.findViewById(R.id.captionText);
        timeTxt = itemView.findViewById(R.id.timeTxt);
        shadowOverlay = itemView.findViewById(R.id.shadowOverlay);
    }
}
