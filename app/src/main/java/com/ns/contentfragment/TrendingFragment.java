package com.ns.contentfragment;

import android.os.Bundle;

import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;

public class TrendingFragment extends BaseFragmentTHP {


    public static TrendingFragment getInstance() {
        TrendingFragment fragment = new TrendingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_trending;
    }
}
