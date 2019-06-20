package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.ns.adapter.PersonaliseAdapter;
import com.ns.thpremium.R;

import java.util.ArrayList;
import java.util.List;

public class THPPersonaliseActivity extends BaseAcitivityTHP {
    ViewPager viewPager;
    PersonaliseAdapter mAdapter;
    List<String> itemsList=new ArrayList<>();

    @Override
    public int layoutRes() {
        return R.layout.activity_thp_personalise;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = findViewById(R.id.personalise_viewPager);
        itemsList.add("Topics");
        itemsList.add("Cities");
        itemsList.add("Authors");

        mAdapter = new PersonaliseAdapter(THPPersonaliseActivity.this, itemsList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mAdapter.getCount()-1);

    }
}
