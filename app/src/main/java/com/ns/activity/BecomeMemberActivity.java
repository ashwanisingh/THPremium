package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.ns.fragment.BecomeMemberIntroFragment;
import com.ns.thpremium.R;
import com.ns.view.CustomTextView;
import com.ns.view.FontCache;

public class BecomeMemberActivity extends BaseAcitivityTHP {

    private CustomTextView signUpFor30Days_Txt;
    private CustomTextView subscribeNowForExclusive_Txt;
    private CustomTextView exploreSubscriptionPlans_Txt;
    private CustomTextView signIn_Txt;


    @Override
    public int layoutRes() {
        return R.layout.activity_becomemember;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BecomeMemberIntroFragment fragment = new BecomeMemberIntroFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.part1, fragment);
        ft.commit();

        signUpFor30Days_Txt = findViewById(R.id.signUpFor30Days_Txt);
        subscribeNowForExclusive_Txt = findViewById(R.id.subscribeNowForExclusive_Txt);
        exploreSubscriptionPlans_Txt = findViewById(R.id.exploreSubscriptionPlans_Txt);
        signIn_Txt = findViewById(R.id.signIn_Txt);


        signUpFor30Days_Txt.applyCustomFont(this, getResources().getString(R.string.FONT_FIRA_SANS_BOLD));
        subscribeNowForExclusive_Txt.applyCustomFont(this, getResources().getString(R.string.FONT_TUNDRA_OFFC_BOLD));
        exploreSubscriptionPlans_Txt.applyCustomFont(this, getResources().getString(R.string.FONT_FIRA_SANS_BOLD));
        signIn_Txt.applyCustomFont(this, getResources().getString(R.string.FONT_FIRA_SANS_REGULAR));

    }


}
