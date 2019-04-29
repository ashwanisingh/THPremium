package com.ns.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joooonho.SelectableRoundedImageView;
import com.ns.thpremium.R;
import com.ns.view.CustomTextView;

public class BriefcaseViewHolder extends RecyclerView.ViewHolder {

    public CustomTextView sectionName;
    public CustomTextView title;
    public CustomTextView authorName_Txt;
    public CustomTextView description_Txt;
    public SelectableRoundedImageView image;
    public View horizontalDivider;

    public BriefcaseViewHolder(@NonNull View itemView) {
        super(itemView);

        sectionName = itemView.findViewById(R.id.sectionName);
        title = itemView.findViewById(R.id.title);
        authorName_Txt = itemView.findViewById(R.id.authorName_Txt);
        description_Txt = itemView.findViewById(R.id.description_Txt);
        image = itemView.findViewById(R.id.image);
        horizontalDivider = itemView.findViewById(R.id.horizontalDivider);

    }
}
