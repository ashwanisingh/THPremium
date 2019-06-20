package com.ns.activity;

import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class THPPersonaliseActivity extends BaseAcitivityTHP {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public int layoutRes() {
        return R.layout.activity_thp_personalise;
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
