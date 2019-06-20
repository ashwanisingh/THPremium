package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netoperation.model.PersonaliseModel;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.thpremium.R;

import java.util.ArrayList;

public class AuthorsRecyclerAdapter extends BaseRecyclerViewAdapter {
    private ArrayList<PersonaliseModel> authorsList;

    public AuthorsRecyclerAdapter(ArrayList<PersonaliseModel> authorsList) {
        authorsList = authorsList;
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

    }

    @Override
    public int getItemCount() {
        return authorsList.size();
    }

    private static class AuthorHolder extends RecyclerView.ViewHolder{
        public AuthorHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
