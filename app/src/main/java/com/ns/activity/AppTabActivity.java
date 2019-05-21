package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.contentfragment.AppTabFragment;
import com.ns.thpremium.BuildConfig;
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

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        AppTabFragment fragment = AppTabFragment.getInstance("");

        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

    }




}
