package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.thpremium.R;

public class SubscriptionPackAdapter extends BaseRecyclerViewAdapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_subscription_pack, viewGroup, false);
        PlanViewHolder holder = new PlanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }


    private class PlanViewHolder extends RecyclerView.ViewHolder {

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
