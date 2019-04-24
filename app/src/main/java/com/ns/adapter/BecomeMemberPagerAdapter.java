package com.ns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ns.thpremium.R;
import com.ns.view.CustomTextView;

public class BecomeMemberPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;

    public BecomeMemberPagerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int contentLayoutResId ;

        if(position == 0) {
            contentLayoutResId = R.layout.fragment_introcontent_1;
        } else {
            contentLayoutResId = R.layout.fragment_introcontent_2;
        }

        final View layout = inflater.inflate(contentLayoutResId, container, false);
        container.addView(layout, 0);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
