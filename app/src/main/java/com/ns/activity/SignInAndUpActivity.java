package com.ns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ns.loginfragment.SignInAndUpFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class SignInAndUpActivity extends BaseAcitivityTHP {

    @Override
    public int layoutRes() {
        return R.layout.activity_sign_in_and_up;
    }

    private String mFrom;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFrom = getIntent().getExtras().getString("from");

        SignInAndUpFragment fragment = SignInAndUpFragment.getInstance(mFrom);
        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);

    }
}
