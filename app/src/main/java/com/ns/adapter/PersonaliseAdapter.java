package com.ns.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.ns.personalisefragment.AuthorsFragment;
import com.ns.personalisefragment.CitiesFragment;
import com.ns.personalisefragment.TopicsFragment;
import com.ns.thpremium.R;

import java.util.List;

public class PersonaliseAdapter extends FragmentStatePagerAdapter {

    public PersonaliseAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        if(i==0) {
            return TopicsFragment.getInstance();
        }
        else if(i==1) {
            return CitiesFragment.getInstance();
        }
        else {
            return AuthorsFragment.getInstance();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
