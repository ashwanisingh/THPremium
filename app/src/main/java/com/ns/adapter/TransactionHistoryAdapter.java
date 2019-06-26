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
import com.ns.utils.ResUtil;

import java.util.List;

public class TransactionHistoryAdapter extends BaseRecyclerViewAdapter {

    private List<TransactionHistoryModel.TxnDataBean> transactions;
    private String mViewTypeFrom;

    public TransactionHistoryAdapter(List<TransactionHistoryModel.TxnDataBean> transactions, String viewTypeFrom) {
        this.transactions = transactions;
        this.mViewTypeFrom = viewTypeFrom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mViewTypeFrom.equalsIgnoreCase("HISTORY")) {
            return new TxnHistoryViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_transaction_history, viewGroup, false));
        } else {
            return new SubsPlanViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_transaction_history_subs_plan, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TransactionHistoryModel.TxnDataBean bean = transactions.get(i);
        if(viewHolder instanceof TxnHistoryViewHolder) {
            TxnHistoryViewHolder holder = (TxnHistoryViewHolder) viewHolder;
            holder.packName_Txt.setText(bean.getPlanName());
            if (bean.getAmount() == 0.0) {
                holder.price_Txt.setVisibility(View.GONE);
            } else {
                holder.price_Txt.setVisibility(View.VISIBLE);
                holder.price_Txt.setText("" + bean.getAmount() + " " + bean.getCurrency());
            }
            holder.status_Txt.setText(bean.getTrxnstatus());
            holder.paymentMode_Txt.setText(bean.getBillingmode());
            holder.date_Txt.setText(bean.getTxnDate());
            if (bean.getTransactionid() == null || TextUtils.isEmpty(bean.getTransactionid())) {
                holder.orderId_Txt.setVisibility(View.GONE);
            } else {
                holder.orderId_Txt.setText(bean.getTransactionid());
                holder.orderId_Txt.setVisibility(View.VISIBLE);
            }
        }
        else if(viewHolder instanceof SubsPlanViewHolder) {
            SubsPlanViewHolder holder = (SubsPlanViewHolder) viewHolder;
            holder.packName_Txt.setText(bean.getPlanName());
            holder.date_Txt.setText(bean.getTxnDate());
            if(bean.getTrxnstatus().equalsIgnoreCase("success")) {
                holder.status_Txt.setTextColor(ResUtil.getColor(holder.itemView.getResources(), R.color.greenColor_1));
            } else{
                holder.status_Txt.setTextColor(ResUtil.getColor(holder.itemView.getResources(), R.color.redColor_1));
            }
            holder.status_Txt.setText(bean.getTrxnstatus());

        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    private static class TxnHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView packName_Txt;
        TextView price_Txt;
        TextView status_Txt;
        TextView paymentMode_Txt;
        TextView date_Txt;
        TextView orderId_Txt;

        public TxnHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            packName_Txt = itemView.findViewById(R.id.planName_Txt);
            price_Txt = itemView.findViewById(R.id.price_Txt);
            status_Txt = itemView.findViewById(R.id.status_Txt);
            paymentMode_Txt = itemView.findViewById(R.id.paymentMode_Txt);
            date_Txt = itemView.findViewById(R.id.date_Txt);
            orderId_Txt = itemView.findViewById(R.id.orderId_Txt);

        }
    }

    private static class SubsPlanViewHolder extends RecyclerView.ViewHolder {

        TextView packName_Txt;
        TextView status_Txt;
        TextView date_Txt;

        public SubsPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            packName_Txt = itemView.findViewById(R.id.orderId_Txt);
            status_Txt = itemView.findViewById(R.id.status_Txt);
            date_Txt = itemView.findViewById(R.id.date_Txt);

        }
    }



}
