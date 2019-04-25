package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.fragment.SubscriptionOptionFragment;
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

        SubscriptionOptionFragment fragment = SubscriptionOptionFragment.getInstance("");
        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);


        // Back button click listener
        findViewById(R.id.backBtn).setOnClickListener(v->{
            finish();
        });
    }



}
