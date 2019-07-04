package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.netoperation.net.ApiManager;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.IntentUtil;
import com.ns.utils.THPConstants;
import com.ns.view.CustomTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DemoActivity extends BaseAcitivityTHP {


    private ImageView premiumLogoBtn;
    private CustomTextView profileBtn;

    @Override
    public int layoutRes() {
        return R.layout.activity_demo;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        premiumLogoBtn = findViewById(R.id.premiumLogoBtn);
        profileBtn = findViewById(R.id.profileBtn);

        premiumLogoBtn.setVisibility(View.GONE);
        profileBtn.setVisibility(View.GONE);

        premiumLogoBtn.setOnClickListener(v->{
            IntentUtil.openMemberActivity(this, "");
        });

        profileBtn.setOnClickListener(v->{
            IntentUtil.openContentListingActivity(this, THPConstants.FROM_USER_PROFILE);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Shows user name
        ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    if(userProfile != null && !TextUtils.isEmpty(userProfile.getFullName())) {
                        profileBtn.setText(userProfile.getFullName().toUpperCase());
                        premiumLogoBtn.setVisibility(View.GONE);
                        profileBtn.setVisibility(View.VISIBLE);
                    } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getEmailId())) {
                        profileBtn.setText(userProfile.getEmailId().toUpperCase());
                        premiumLogoBtn.setVisibility(View.GONE);
                        profileBtn.setVisibility(View.VISIBLE);
                    } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getContact())) {
                        profileBtn.setText(userProfile.getContact().toUpperCase());
                        premiumLogoBtn.setVisibility(View.GONE);
                        profileBtn.setVisibility(View.VISIBLE);
                    } else {
                        profileBtn.setVisibility(View.GONE);
                        premiumLogoBtn.setVisibility(View.VISIBLE);
                    }
                });
    }
}
