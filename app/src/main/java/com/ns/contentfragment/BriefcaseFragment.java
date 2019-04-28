package com.ns.contentfragment;

import android.os.Bundle;

import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;

public class BriefcaseFragment extends BaseFragmentTHP {


    public static BriefcaseFragment getInstance() {
        BriefcaseFragment fragment = new BriefcaseFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_briefcase;
    }
}
