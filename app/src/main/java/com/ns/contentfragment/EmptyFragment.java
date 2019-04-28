package com.ns.contentfragment;

import android.os.Bundle;

import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;

public class EmptyFragment extends BaseFragmentTHP {


    public static EmptyFragment getInstance() {
        EmptyFragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_empty;
    }
}
