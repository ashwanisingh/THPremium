package com.ns.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ns.fragment.SignInFragment;
import com.ns.fragment.SignUpFragment;
import com.ns.thpremium.R;

public class SignInAndUpPagerAdapter extends FragmentStatePagerAdapter {

    public SignInAndUpPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0) {
            return SignUpFragment.getInstance();
        }
        else {
            return SignInFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "Sign Up";
        }
        else {
            return "Sign In";
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        if (bundle != null) {
            bundle.putParcelableArray("states", null);
            return bundle;
        }
        return super.saveState();
    }

    public View getTabView(int position, Context context, boolean isSelected) {

        String tilte = "";

        if(position == 0) {
            tilte = "Sign Up";
        }
        else {
            tilte = "Sign In";
        }

        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(tilte);
        return v;
    }

    public void SetOnSelectView(Context context, TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.textView);
        iv_text.setTextColor(context.getResources().getColor(R.color.boldBlackColor));
    }

    public void SetUnSelectView(Context context, TabLayout tabLayout,int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.textView);
        iv_text.setTextColor(context.getResources().getColor(R.color.greyColor_1));
    }
}
