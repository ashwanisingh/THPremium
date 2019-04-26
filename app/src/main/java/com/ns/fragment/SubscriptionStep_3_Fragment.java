package com.ns.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

public class SubscriptionStep_3_Fragment extends BaseFragmentTHP {

    private String mFrom;

    public static SubscriptionStep_3_Fragment getInstance(String from) {
        SubscriptionStep_3_Fragment fragment = new SubscriptionStep_3_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SubscriptionPackFragment fragment = SubscriptionPackFragment.getInstance(THPConstants.FROM_SubscriptionStep_1_Fragment);
        FragmentUtil.pushFragmentFromFragment(this, R.id.subscriptionPlansLayout, fragment);

        view.findViewById(R.id.bottomChoosePlanLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }
}
