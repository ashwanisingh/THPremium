package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.thpremium.R;
import com.ns.userprofilefragment.UserProfileFragment;
import com.ns.utils.FragmentUtil;

public class UserProfileActivity extends BaseAcitivityTHP {
    @Override
    public int layoutRes() {
        return R.layout.activity_userprofile;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserProfileFragment fragment = UserProfileFragment.getInstance("");
        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
    }
}
