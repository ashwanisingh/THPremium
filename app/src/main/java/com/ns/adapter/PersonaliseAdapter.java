package com.ns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ns.thpremium.R;

import java.util.ArrayList;
import java.util.List;

public class PersonaliseAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<String> items=new ArrayList<>();

    public PersonaliseAdapter(Context context, List<String> items) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.personalise_view_pager_row_items, container, false);
        TextView tv_items = itemView.findViewById(R.id.tv_items);
        tv_items.setText(items.get(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
