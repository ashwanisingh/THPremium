package com.ns.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.view.CustomTextView;

public class SubscriptionStep_2_Fragment extends BaseFragmentTHP {

    private String mFrom;

    private CustomTextView packName_Txt;
    private CustomTextView planValidity_Txt;

    public static SubscriptionStep_2_Fragment getInstance(String from) {
        SubscriptionStep_2_Fragment fragment = new SubscriptionStep_2_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_2;
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

        packName_Txt = view.findViewById(R.id.packName_Txt);
        planValidity_Txt = view.findViewById(R.id.planValidity_Txt);


        // Change Button click listener
        view.findViewById(R.id.changeBtn_Txt).setOnClickListener(v->{
            SubscriptionStep_3_Fragment fragment = SubscriptionStep_3_Fragment.getInstance("");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

    }
}
