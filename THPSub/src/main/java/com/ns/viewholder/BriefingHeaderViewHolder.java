package com.ns.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.view.CustomTextView;

public class BriefingHeaderViewHolder extends RecyclerView.ViewHolder {

    public CustomTextView editionBtn_Txt;
    public CustomTextView yourEditionFor_Txt;
    public CustomTextView userName_Txt;

    public BriefingHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        userName_Txt = itemView.findViewById(R.id.userName_Txt);
        yourEditionFor_Txt = itemView.findViewById(R.id.yourEditionFor_Txt);
        editionBtn_Txt = itemView.findViewById(R.id.editionBtn_Txt);
    }

}
