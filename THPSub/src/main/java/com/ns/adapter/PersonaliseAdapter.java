package com.ns.adapter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.ns.personalisefragment.AuthorsFragment;
import com.ns.personalisefragment.CitiesFragment;
import com.ns.personalisefragment.TopicsFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonaliseAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mPersonaliseFragments;
    String mTopics, mCities, mAuthors;

    public PersonaliseAdapter(FragmentManager fm, List<Fragment> personaliseFragments, String topics, String cities, String authors) {
        super(fm);
        mPersonaliseFragments = personaliseFragments;
        mTopics=topics;
        mCities=cities;
        mAuthors=authors;
    }

    @Override
    public Fragment getItem(int i) {
        return mPersonaliseFragments.get(i);
    }

    @Override
    public int getCount() {
        return mPersonaliseFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mTopics;
            case 1:
                return mCities;
            case 2:
                return mAuthors;
        }
        return null;
    }
}
