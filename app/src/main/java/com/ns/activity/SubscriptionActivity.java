package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.fragment.SubscriptionPackFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class SubscriptionActivity extends BaseAcitivityTHP {


    @Override
    public int layoutRes() {
        return R.layout.activity_subscription;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SubscriptionPackFragment fragment = SubscriptionPackFragment.getInstance("");
        FragmentUtil.pushFragment(this, R.id.parentLayout, fragment);
    }
}
