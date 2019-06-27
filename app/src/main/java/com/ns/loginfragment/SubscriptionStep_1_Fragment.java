package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

public class SubscriptionStep_1_Fragment extends BaseFragmentTHP {

    private String mFrom;

    public static SubscriptionStep_1_Fragment getInstance(String from) {
        SubscriptionStep_1_Fragment fragment = new SubscriptionStep_1_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_1;
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

       // BecomeMemberIntroFragment benefitsFragment = BecomeMemberIntroFragment.getInstance(THPConstants.FROM_SubscriptionStep_1_Fragment);
       // FragmentUtil.pushFragmentFromFragment(this, R.id.benefitsLayout, benefitsFragment);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            getActivity().finish();
        });
    }
}
