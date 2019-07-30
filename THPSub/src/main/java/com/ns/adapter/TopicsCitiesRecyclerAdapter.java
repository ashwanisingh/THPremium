package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netoperation.model.PersonaliseDetails;
import com.netoperation.model.PersonaliseModel;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.utils.ContentUtil;
import com.ns.utils.GlideUtil;
import com.ns.view.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class TopicsCitiesRecyclerAdapter extends BaseRecyclerViewAdapter {
    PersonaliseDetails details;
    THPPersonaliseItemClickListener itemClickListener;
    String imageUrl="https://subscription.thehindu.com/";
    String mFrom;

    public TopicsCitiesRecyclerAdapter(PersonaliseDetails details, String mFrom, THPPersonaliseItemClickListener itemClickListener) {
        this.details = details;
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
        GlideUtil.loadImage(holder.image.getContext(), holder.image, (imageUrl+details.getValues().get(i).getImage()));
        holder.tv_topics_cities.setText(details.getValues().get(i).getTitle());
        PersonaliseModel model = details.getValues().get(i);
        if(model.isSelected()) {
            holder.btn_personalise.setImageResource(R.drawable.ic_tik);
        } else  {
            holder.btn_personalise.setImageResource(R.drawable.ic_plus);
        }

        holder.btn_personalise.setOnClickListener(v -> {
            model.setSelected(!model.isSelected());
            if (itemClickListener != null) {
                itemClickListener.personaliseItemClick(details.getValues().get(i), mFrom);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return details.getValues().size();
    }

    private static class TopicsCitiesHolder extends RecyclerView.ViewHolder{
        ImageView image;
        ImageView btn_personalise;
        CustomTextView tv_topics_cities;

        public TopicsCitiesHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            btn_personalise = itemView.findViewById(R.id.btn_personalise);
            tv_topics_cities = itemView.findViewById(R.id.tv_topics_cities);
        }
    }
}
