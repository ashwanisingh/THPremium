package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ns.adapter.AppTabPagerAdapter;
import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;
import com.ns.utils.FragmentUtil;

public class AppTabFragment extends BaseFragmentTHP {

    public static AppTabFragment getInstance(String from) {
        AppTabFragment fragment = new AppTabFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private LinearLayout appTabsMore_Img;
    private AppTabPagerAdapter pagerAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_apptab;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mTabLayout = view.findViewById(R.id.appTabsTabLayout);
        viewPager = view.findViewById(R.id.appTabsViewPager);

        pagerAdapter = new AppTabPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(pagerAdapter);

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

        // More tab Click Listener
        view.findViewById(R.id.appTabsMore_Img).setOnClickListener(v->{

        });

    }
}
