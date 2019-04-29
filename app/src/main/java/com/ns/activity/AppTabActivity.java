package com.ns.activity;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.ns.adapter.AppTabPagerAdapter;
import com.ns.alerts.Alerts;
import com.ns.contentfragment.AppTabFragment;
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

        AppTabFragment fragment = AppTabFragment.getInstance("");

        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

        netCheck();
    }


    private void netCheck() {

        ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if(connectivity.state() == NetworkInfo.State.CONNECTED) {

                    }
                    else {
                        Alerts.noInternetSnackbar(findViewById(R.id.parentLayout));
                    }

                    Log.i("", "");
                });
    }

}
