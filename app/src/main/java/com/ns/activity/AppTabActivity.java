package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.ns.adapter.AppTabPagerAdapter;
import com.ns.contentfragment.AppTabFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class AppTabActivity extends BaseAcitivityTHP {


    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppTabFragment fragment = AppTabFragment.getInstance("");

        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

    }


}
