package com.ns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.netoperation.net.ApiManager;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.alerts.Alerts;
import com.ns.contentfragment.AppTabFragment;
import com.ns.payment.IabException;
import com.ns.payment.IabHelper;
import com.ns.payment.IabResult;
import com.ns.payment.Inventory;
import com.ns.payment.Purchase;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppTabActivity extends BaseAcitivityTHP {

    private IabHelper mHelper;

    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    AppTabFragment fragment = AppTabFragment.getInstance("", userProfile.getUserId());

                    FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
                });

    }


}
