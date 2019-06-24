package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.netoperation.model.PersonaliseModel;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.view.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class TopicsCitiesRecyclerAdapter extends BaseRecyclerViewAdapter {

    ArrayList<PersonaliseModel> modelList;
    THPPersonaliseItemClickListener itemClickListener;
    String mFrom;

    public TopicsCitiesRecyclerAdapter(ArrayList<PersonaliseModel> modelList, String mFrom, THPPersonaliseItemClickListener itemClickListener) {
        this.modelList = modelList;
        this.itemClickListener=itemClickListener;
        this.mFrom=mFrom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.topics_row_item, viewGroup, false);
        TopicsCitiesRecyclerAdapter.TopicsCitiesHolder holder = new TopicsCitiesRecyclerAdapter.TopicsCitiesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TopicsCitiesHolder holder = (TopicsCitiesHolder) viewHolder;
        holder.tv_topics_cities.setText(modelList.get(i).getName());
        PersonaliseModel model = modelList.get(i);
        if(model.getSelected()) {
            holder.btn_personalise.setImageResource(R.drawable.ic_tik);
        } else  {
            holder.btn_personalise.setImageResource(R.drawable.ic_plus);
        }

        holder.btn_personalise.setOnClickListener(v -> {
            model.setSelected(!model.getSelected());
            if (itemClickListener != null) {
                itemClickListener.personaliseItemClick(modelList.get(i), mFrom);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private static class TopicsCitiesHolder extends RecyclerView.ViewHolder{
        ImageView btn_personalise;
        CustomTextView tv_topics_cities;

        public TopicsCitiesHolder(@NonNull View itemView) {
            super(itemView);
            btn_personalise = itemView.findViewById(R.id.btn_personalise);
            tv_topics_cities = itemView.findViewById(R.id.tv_topics_cities);
        }
    }
}
