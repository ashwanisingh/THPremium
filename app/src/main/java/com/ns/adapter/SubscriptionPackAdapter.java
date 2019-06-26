package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netoperation.model.UserPlanListBean;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.thpremium.R;

import java.util.List;

public class SubscriptionPackAdapter extends BaseRecyclerViewAdapter {

    private String mFrom;
    private List<UserPlanListBean> mPlanInfoList;
    public SubscriptionPackAdapter(String from, List<UserPlanListBean> planInfoList){
        mFrom = from;
        mPlanInfoList = planInfoList;
    }


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
        PlanViewHolder holder = (PlanViewHolder) viewHolder;

        holder.subscribeBtn_Txt.setOnClickListener(v->{
            // TODO, Open Google Pay Subscription, for payment
        });

    }

    @Override
    public int getItemCount() {
        return mPlanInfoList.size();
    }


    private class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView packName_Txt;
        TextView planValidity_Txt;
        TextView currencyValue_Txt;
        TextView planOffer_Txt;
        TextView subscribeBtn_Txt;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            packName_Txt = itemView.findViewById(R.id.packName_Txt);
            planValidity_Txt = itemView.findViewById(R.id.planValidity_Txt);
            currencyValue_Txt = itemView.findViewById(R.id.currencyValue_Txt);
            planOffer_Txt = itemView.findViewById(R.id.planOffer_Txt);
            subscribeBtn_Txt = itemView.findViewById(R.id.subscribeBtn_Txt);

        }
    }

}
