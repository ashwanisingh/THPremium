package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netoperation.net.ApiManager;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.contentfragment.AppTabFragment;
import com.ns.loginfragment.AccountCreatedFragment;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppTabActivity extends BaseAcitivityTHP {

    private String mFrom;


    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null && getIntent().getExtras()!= null) {
            mFrom = getIntent().getStringExtra("from");
        }

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    AppTabFragment fragment = AppTabFragment.getInstance("", userProfile.getUserId());

                    FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

                    // THis below condition will be executed when user creates normal Sign-UP
                    if(mFrom != null && !TextUtils.isEmpty(mFrom) && mFrom.equalsIgnoreCase("SignUp")) {
                        AccountCreatedFragment accountCreated = AccountCreatedFragment.getInstance("");

                        FragmentUtil.addFragmentAnim(this, R.id.parentLayout, accountCreated, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
                    }
                });

    }


}
