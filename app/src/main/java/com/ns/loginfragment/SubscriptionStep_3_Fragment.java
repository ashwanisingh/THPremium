package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.netoperation.net.ApiManager;
import com.ns.adapter.TransactionHistoryAdapter;
import com.ns.alerts.Alerts;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;
import com.ns.view.RecyclerViewPullToRefresh;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SubscriptionStep_3_Fragment extends BaseFragmentTHP {

    private String mFrom;
    private RecyclerViewPullToRefresh mRecyclerViewPullToRefresh;

    public static SubscriptionStep_3_Fragment getInstance(String from) {
        SubscriptionStep_3_Fragment fragment = new SubscriptionStep_3_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SubscriptionPackFragment fragment = SubscriptionPackFragment.getInstance(THPConstants.FROM_SubscriptionStep_1_Fragment);
        FragmentUtil.pushFragmentFromFragment(this, R.id.subscriptionPlansLayout, fragment);

        mRecyclerViewPullToRefresh = view.findViewById(R.id.recyclerViewPullToRefresh);
        mRecyclerViewPullToRefresh.enablePullToRefresh(false);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity())
        );

        loadData();

    }

    private void loadData() {
        mRecyclerViewPullToRefresh.showProgressBar();
        mDisposable.add(ApiManager.getUserProfile(getActivity())
                .subscribe(userProfile ->
                                ApiManager.getTxnHistory(userProfile.getUserId())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(txnDataBeans -> {
                                            TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(txnDataBeans, "SUBS_PLAN");
                                            mRecyclerViewPullToRefresh.setDataAdapter(adapter);
                                        }, throwable -> {
                                            mRecyclerViewPullToRefresh.hideProgressBar();
                                            Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                        }, () -> {
                                            mRecyclerViewPullToRefresh.hideProgressBar();
                                        })
                        , throwable -> {
                            mRecyclerViewPullToRefresh.hideProgressBar();
                        }));
    }
}
