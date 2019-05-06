package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.thpremium.R;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends BaseRecyclerViewAdapter {

    private ArrayList<String> transactions;

    public TransactionHistoryAdapter(ArrayList<String> transactions) {
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



    }

    @Override
    public int getItemCount() {
        return 4;
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
