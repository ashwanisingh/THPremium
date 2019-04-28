package com.ns.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ns.contentfragment.BookmarksFragment;
import com.ns.contentfragment.BriefcaseFragment;
import com.ns.contentfragment.DashboardFragment;
import com.ns.contentfragment.EmptyFragment;
import com.ns.contentfragment.TrendingFragment;
import com.ns.thpremium.R;

public class AppTabPagerAdapter extends FragmentStatePagerAdapter {

    public AppTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0) {
            return DashboardFragment.getInstance();
        }
        else if(i==1) {
            return BriefcaseFragment.getInstance();
        }
        else if(i==2) {
            return TrendingFragment.getInstance();
        }
        else if(i==3) {
            return BookmarksFragment.getInstance();
        }
        else if(i==4) {
            return EmptyFragment.getInstance();
        }
        else {
            return EmptyFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    private String [] tabNames = {"Dashboard", "Briefcase", "Trending", "Bookmarks", "More"};
    private int [] tabUnSelectedIcons = {R.drawable.bi_1, R.drawable.bi_2, R.drawable.bi_3,
            R.drawable.bi_3, R.drawable.bi_4, R.drawable.bi_5};
    private int [] tabSelectedIcons = {R.drawable.ic_google, R.drawable.ic_google, R.drawable.ic_google,
            R.drawable.ic_google, R.drawable.ic_google, R.drawable.ic_google};

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
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
        View v = LayoutInflater.from(context).inflate(R.layout.apptab_custom_tab, null);
        TextView tv = v.findViewById(R.id.tab_Txt);
        ImageView tabIcon = v.findViewById(R.id.tab_Icon);
        tabIcon.setImageResource(tabUnSelectedIcons[position]);
        tv.setText(tabNames[position]);
        return v;
    }

    public void SetOnSelectView(Context context, TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tab_Txt);
        ImageView tabIcon = selected.findViewById(R.id.tab_Icon);
        tabIcon.setImageResource(tabSelectedIcons[position]);
        iv_text.setTextColor(context.getResources().getColor(R.color.boldBlackColor));
    }

    public void SetUnSelectView(Context context, TabLayout tabLayout,int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tab_Txt);
        ImageView tabIcon = selected.findViewById(R.id.tab_Icon);
        tabIcon.setImageResource(tabUnSelectedIcons[position]);
        iv_text.setTextColor(context.getResources().getColor(R.color.greyColor_1));
    }
}
