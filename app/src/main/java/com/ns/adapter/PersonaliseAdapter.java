package com.ns.adapter;
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

    public PersonaliseAdapter(FragmentManager fm, List<Fragment> personaliseFragments) {
        super(fm);
        mPersonaliseFragments = personaliseFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mPersonaliseFragments.get(i);
    }

    @Override
    public int getCount() {
        return mPersonaliseFragments.size();
    }
}
