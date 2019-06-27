package com.ns.loginfragment;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.ns.activity.BaseAcitivityTHP;
import com.ns.alerts.Alerts;
import com.ns.thpremium.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseFragmentTHP extends Fragment {

    public abstract int getLayoutRes();

    protected boolean mIsOnline;
    protected boolean mIsVisible;
    protected int mSize = 10;
    protected String mUserId;

    protected BaseAcitivityTHP mActivity;

    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutRes(), container, false);
        netCheck(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsVisible = isVisibleToUser;
    }

    private void netCheck(View rootview) {
        mDisposable.add(ReactiveNetwork
                .observeNetworkConnectivity(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if(connectivity.state() == NetworkInfo.State.CONNECTED) {
                        mIsOnline = true;
                    }
                    else {
                        mIsOnline = false;
                        if(mIsVisible && rootview!=null) {
                            //Alerts.noInternetSnackbar(rootview);
                            Alerts.showSnackbar(getActivity(), getResources().getString(R.string.please_check_ur_connectivity));
                        }
                    }

                    Log.i("", "");
                }));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDisposable.clear();
    }

}
