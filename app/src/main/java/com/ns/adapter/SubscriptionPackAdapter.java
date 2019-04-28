package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.userfragment.SubscriptionStep_2_Fragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class SubscriptionPackAdapter extends BaseRecyclerViewAdapter {

    private String mFrom;
    public SubscriptionPackAdapter(String from){
        mFrom = from;
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
            SubscriptionStep_2_Fragment step2Fragment = SubscriptionStep_2_Fragment.getInstance(mFrom);
            FragmentUtil.pushFragmentAnim((AppCompatActivity)v.getContext(), R.id.parentLayout,
                    step2Fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

    }

    @Override
    public int getItemCount() {
        return 4;
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