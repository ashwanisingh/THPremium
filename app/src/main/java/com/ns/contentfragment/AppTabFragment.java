package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.ns.activity.BecomeMemberActivity;
import com.ns.adapter.AppTabPagerAdapter;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.view.ViewPagerScroller;

import java.lang.reflect.Field;

public class AppTabFragment extends BaseFragmentTHP {

    private ConstraintLayout subscribeLayout;

    public static AppTabFragment getInstance(String from) {
        AppTabFragment fragment = new AppTabFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private AppTabPagerAdapter pagerAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_apptab;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        subscribeLayout = view.findViewById(R.id.subscribeLayout);
        mTabLayout = view.findViewById(R.id.appTabsTabLayout);
        viewPager = view.findViewById(R.id.appTabsViewPager);

        pagerAdapter = new AppTabPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        // This is smooth scroll of ViewPager
        smoothPagerScroll();

        mTabLayout.setupWithViewPager(viewPager, true);


        // Iterate over all tabs and set the custom view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i, getActivity(), false));
        }

        // To select default tab
        pagerAdapter.SetOnSelectView(getActivity(), mTabLayout, 0);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                pagerAdapter.SetOnSelectView(getActivity(), mTabLayout, pos);
                viewPager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                pagerAdapter.SetUnSelectView(getActivity(), mTabLayout, pos);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        view.findViewById(R.id.subscribeBtn_Txt).setOnClickListener(v->{
            IntentUtil.openSubscriptionActivity(getActivity(), "freeTrial");
        });


        // Back Button Click Listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->
            getActivity().finish()
        );

        // Premium Logo Button Click Listener
        view.findViewById(R.id.premiumLogoBtn).setOnClickListener(v->
            IntentUtil.openMemberActivity(getActivity(), "")
        );

        // Profile Icon Button Click Listener
        view.findViewById(R.id.profileBtn).setOnClickListener(v->
            IntentUtil.openUserProfileActivity(getActivity(), "")
        );

    }

    /**
     * This is ViewPager Page Scroll Animation
     */
    private void smoothPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, new ViewPagerScroller(getActivity(),
                    new LinearInterpolator(), 250));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
