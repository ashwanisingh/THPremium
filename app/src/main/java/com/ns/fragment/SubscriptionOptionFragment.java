package com.ns.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

public class SubscriptionOptionFragment extends BaseFragmentTHP {

    private String mFrom;

    public static SubscriptionOptionFragment getInstance(String from) {
        SubscriptionOptionFragment fragment = new SubscriptionOptionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_option;
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

        SubscriptionPackFragment fragment = SubscriptionPackFragment.getInstance("");
        FragmentUtil.pushFragmentFromFragment(this, R.id.subscriptionPlansLayout, fragment);

        BecomeMemberIntroFragment benefitsFragment = BecomeMemberIntroFragment.getInstance(THPConstants.FROM_SubscriptionOptionFragment);
        FragmentUtil.pushFragmentFromFragment(this, R.id.benefitsLayout, benefitsFragment);
    }
}
