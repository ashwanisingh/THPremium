package com.ns.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.view.AutoResizeWebview;

public class DetailDescriptionWebViewHolder extends RecyclerView.ViewHolder {
    public AutoResizeWebview mLeadTxt;
    public AutoResizeWebview webview;

    public DetailDescriptionWebViewHolder(View itemView) {
        super(itemView);
        webview = itemView.findViewById(R.id.webview);
        mLeadTxt = itemView.findViewById(R.id.leadTxt);
    }
}
