package com.ns.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ns.adapter.BecomeMemberPagerAdapter;
import com.ns.thpremium.R;
import com.ns.view.CustomTextView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BecomeMemberIntroFragment extends BaseFragmentTHP {

    private ViewPager mViewPager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_become_member_intro;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = view.findViewById(R.id.becomeMember_ViewPager);

        mViewPager.setAdapter(new BecomeMemberPagerAdapter(getActivity()));

        DotsIndicator tabLayout = view.findViewById(R.id.dots_indicator);
        tabLayout.setViewPager(mViewPager);

    }
}
