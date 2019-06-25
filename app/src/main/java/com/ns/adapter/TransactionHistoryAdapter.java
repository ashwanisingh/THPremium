package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netoperation.model.TransactionHistoryModel;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.thpremium.R;

import java.util.List;

public class TransactionHistoryAdapter extends BaseRecyclerViewAdapter {

    private List<TransactionHistoryModel.TxnDataBean> transactions;

    public TransactionHistoryAdapter(List<TransactionHistoryModel.TxnDataBean> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_transaction_history, viewGroup, false);
        PlanViewHolder holder = new PlanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PlanViewHolder holder = (PlanViewHolder) viewHolder;
        TransactionHistoryModel.TxnDataBean bean = transactions.get(i);

        holder.packName_Txt.setText(bean.getPlanName());
        if(bean.getAmount() == 0.0) {
            holder.price_Txt.setVisibility(View.GONE);
        } else {
            holder.price_Txt.setVisibility(View.VISIBLE);
            holder.price_Txt.setText("" + bean.getAmount() + " " + bean.getCurrency());
        }
        holder.status_Txt.setText(bean.getTrxnstatus());
        holder.paymentMode_Txt.setText(bean.getBillingmode());
        holder.date_Txt.setText(bean.getTxnDate());
        if(bean.getTransactionid() == null || TextUtils.isEmpty(bean.getTransactionid())) {
            holder.orderId_Txt.setVisibility(View.GONE);
        } else {
            holder.orderId_Txt.setText(bean.getTransactionid());
            holder.orderId_Txt.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    private static class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView packName_Txt;
        TextView price_Txt;
        TextView status_Txt;
        TextView paymentMode_Txt;
        TextView date_Txt;
        TextView orderId_Txt;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            packName_Txt = itemView.findViewById(R.id.planName_Txt);
            price_Txt = itemView.findViewById(R.id.price_Txt);
            status_Txt = itemView.findViewById(R.id.status_Txt);
            paymentMode_Txt = itemView.findViewById(R.id.paymentMode_Txt);
            date_Txt = itemView.findViewById(R.id.date_Txt);
            orderId_Txt = itemView.findViewById(R.id.orderId_Txt);

        }
    }



}
