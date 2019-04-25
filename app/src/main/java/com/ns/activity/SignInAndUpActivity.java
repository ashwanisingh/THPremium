package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ns.adapter.SignInAndUpPagerAdapter;
import com.ns.thpremium.R;

public class SignInAndUpActivity extends BaseAcitivityTHP {

    @Override
    public int layoutRes() {
        return R.layout.activity_sign_in_and_up;
    }

    private String mFrom;

    private ViewPager mSignInUpViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFrom = getIntent().getExtras().getString("from");

        int selectedPosition = 0;

        if(mFrom != null && mFrom.equalsIgnoreCase("signIn")) {
            selectedPosition = 1;
        } else {
            selectedPosition = 0;
        }

        mSignInUpViewPager = findViewById(R.id.signInUpViewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        SignInAndUpPagerAdapter pagerAdapter = new SignInAndUpPagerAdapter(getSupportFragmentManager());
        mSignInUpViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mSignInUpViewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i, this, false));
        }

        // To select default tab
        pagerAdapter.SetOnSelectView(SignInAndUpActivity.this, mTabLayout, selectedPosition);

        mSignInUpViewPager.setCurrentItem(selectedPosition);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                pagerAdapter.SetOnSelectView(SignInAndUpActivity.this, mTabLayout, pos);
                mSignInUpViewPager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                pagerAdapter.SetUnSelectView(SignInAndUpActivity.this, mTabLayout, pos);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
