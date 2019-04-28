package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.userfragment.SubscriptionStep_1_Fragment;
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

        SubscriptionStep_1_Fragment fragment = SubscriptionStep_1_Fragment.getInstance("");
        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);


    }



}
