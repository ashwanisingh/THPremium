package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netoperation.model.TxnDataBean;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.callbacks.OnSubscribeBtnClick;
import com.ns.thpremium.R;

import java.util.List;

public class SubscriptionPackAdapter extends BaseRecyclerViewAdapter {

    private OnSubscribeBtnClick mOnSubscribeBtnClick;

    private String mFrom;
    private List<TxnDataBean> mPlanInfoList;

    private boolean isEmpty = false;

    public SubscriptionPackAdapter(String from, List<TxnDataBean> planInfoList, OnSubscribeBtnClick onSubscribeBtnClick){
        mFrom = from;
        mPlanInfoList = planInfoList;
        mOnSubscribeBtnClick = onSubscribeBtnClick;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty, List<TxnDataBean> planInfoList) {
        isEmpty = empty;
        mPlanInfoList = planInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(isEmpty()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_subscription_empty, viewGroup, false);
            EmptyViewHolder holder = new EmptyViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_subscription_pack, viewGroup, false);
            PlanViewHolder holder = new PlanViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TxnDataBean bean = mPlanInfoList.get(i);

        if(viewHolder instanceof PlanViewHolder) {
            PlanViewHolder holder = (PlanViewHolder) viewHolder;

            holder.packName_Txt.setText(bean.getPlanName());
            holder.planValidity_Txt.setText(bean.getValidity());
            if (bean.getAmount() == 0.0) {
                holder.currencyLayout.setVisibility(View.INVISIBLE);
            } else {
                holder.currencyLayout.setVisibility(View.VISIBLE);
                holder.currencyValue_Txt.setText("" + bean.getAmount());
            }
            // holder.planOffer_Txt.setText("");
            if (bean.getIsActive() == 1) {
                holder.subscribeBtn_Txt.setText("Subscribed");
            } else {
                holder.subscribeBtn_Txt.setText("Subscribe");
            }

            holder.subscribeBtn_Txt.setOnClickListener(v -> {
                if (bean.getIsActive() == 1) {
                    return;
                }

                if (mOnSubscribeBtnClick != null) {
                    mOnSubscribeBtnClick.onSubscribeBtnClick(bean);
                }

                // TODO, Open Google Pay Subscription, for payment
            });
        }
        else if(viewHolder instanceof EmptyViewHolder) {

        }

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
        LinearLayout currencyLayout;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            packName_Txt = itemView.findViewById(R.id.packName_Txt);
            planValidity_Txt = itemView.findViewById(R.id.planValidity_Txt);
            currencyValue_Txt = itemView.findViewById(R.id.currencyValue_Txt);
            planOffer_Txt = itemView.findViewById(R.id.planOffer_Txt);
            subscribeBtn_Txt = itemView.findViewById(R.id.subscribeBtn_Txt);
            currencyLayout = itemView.findViewById(R.id.currencyLayout);

        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }





}
