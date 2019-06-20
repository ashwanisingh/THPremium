package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.adapter.PersonaliseAdapter;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import java.util.ArrayList;
import java.util.List;

public class THPPersonaliseActivity extends BaseAcitivityTHP {
    ViewPager viewPager;
    PersonaliseAdapter mAdapter;
    List<String> itemsList=new ArrayList<>();

    protected final CompositeDisposable mDisposable = new CompositeDisposable();


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

    private void loadData() {
        mDisposable.add(ApiManager.getPrefList(NetConstants.USER_ID, BuildConfig.SITEID, NetConstants.ITEM_SIZE, NetConstants.RECO_ALL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {

                        }, throwable -> {

                        }, () -> {

                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mDisposable.clear();
    }
}
