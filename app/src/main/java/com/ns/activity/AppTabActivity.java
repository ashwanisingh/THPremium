package com.ns.activity;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.adapter.AppTabPagerAdapter;
import com.ns.alerts.Alerts;
import com.ns.contentfragment.AppTabFragment;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppTabActivity extends BaseAcitivityTHP {


    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        AppTabFragment fragment = AppTabFragment.getInstance("");

        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

    }




}
