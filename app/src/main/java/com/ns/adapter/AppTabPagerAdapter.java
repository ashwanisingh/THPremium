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
import com.ns.contentfragment.EditionOptionFragment;
import com.ns.contentfragment.SuggestedFragment;
import com.ns.contentfragment.TrendingFragment;
import com.ns.thpremium.R;

public class AppTabPagerAdapter extends FragmentStatePagerAdapter {

    public AppTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0) {
            return BriefcaseFragment.getInstance();
        }
        else if(i==1) {
            return DashboardFragment.getInstance();
        }
        else if(i==2) {
            return SuggestedFragment.getInstance();
        }
        else if(i==3) {
            return TrendingFragment.getInstance();
        }
        else if(i==4) {
            return EditionOptionFragment.getInstance();
        }
        else {
            return EditionOptionFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    private String [] tabNames = {"Briefing", "My Stories", "Suggested", "Trending", "More"};
    private int [] tabUnSelectedIcons = {
            R.drawable.tab_briefcase_unselected,
            R.drawable.tab_dashboard_unselected,
            R.drawable.tab_suggested_unselected,
            R.drawable.tab_trending_unselected,
            R.drawable.tab_more_grey};
    private int [] tabSelectedIcons = {
            R.drawable.tab_briefcase_selected,
            R.drawable.tab_dashboard_selected,
            R.drawable.tab_suggested_selected,
            R.drawable.tab_trending_selected,
            R.drawable.tab_more_grey};

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
