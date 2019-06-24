package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.netoperation.model.PersonaliseModel;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.thpremium.R;

import java.util.ArrayList;

public class AuthorsRecyclerAdapter extends BaseRecyclerViewAdapter {

    ArrayList<PersonaliseModel> modelList;
    THPPersonaliseItemClickListener itemClickListener;
    String mFrom;

    public AuthorsRecyclerAdapter(ArrayList<PersonaliseModel> modelList, String mFrom, THPPersonaliseItemClickListener itemClickListener) {
        this.modelList = modelList;
        this.itemClickListener=itemClickListener;
        this.mFrom=mFrom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.authors_row_items, viewGroup, false);
        AuthorsRecyclerAdapter.AuthorHolder holder = new AuthorsRecyclerAdapter.AuthorHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AuthorHolder holder = (AuthorHolder) viewHolder;
        holder.tv_author_name.setText(modelList.get(i).getName());
        PersonaliseModel model = modelList.get(i);
        if(model.getSelected()) {
            holder.imageview_click.setImageResource(R.drawable.ic_tik_1);
        } else  {
            holder.imageview_click.setImageResource(R.drawable.ic_plus_1);
        }
            holder.imageview_click.setOnClickListener(v -> {
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

    private static class AuthorHolder extends RecyclerView.ViewHolder{

        SelectableRoundedImageView image_author;
        TextView tv_author_name;
        SelectableRoundedImageView imageview_click;

        public AuthorHolder(@NonNull View itemView) {
            super(itemView);
            image_author = itemView.findViewById(R.id.image_author);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            imageview_click = itemView.findViewById(R.id.imageview_click);
        }
    }
}
