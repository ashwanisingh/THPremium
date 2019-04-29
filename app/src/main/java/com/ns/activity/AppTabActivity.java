package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.ns.adapter.AppTabPagerAdapter;
import com.ns.thpremium.R;

public class AppTabActivity extends BaseAcitivityTHP {

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private LinearLayout appTabsMore_Img;
    private AppTabPagerAdapter pagerAdapter;


    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabLayout = findViewById(R.id.appTabsTabLayout);
        viewPager = findViewById(R.id.appTabsViewPager);
        appTabsMore_Img = findViewById(R.id.appTabsMore_Img);

        pagerAdapter = new AppTabPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(viewPager, true);


        // Iterate over all tabs and set the custom view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i, this, false));
        }

        // To select default tab
        pagerAdapter.SetOnSelectView(this, mTabLayout, 0);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                    pagerAdapter.SetOnSelectView(AppTabActivity.this, mTabLayout, pos);
                    viewPager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                pagerAdapter.SetUnSelectView(AppTabActivity.this, mTabLayout, pos);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
