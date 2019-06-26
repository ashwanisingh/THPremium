package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.netoperation.net.ApiManager;
import com.ns.adapter.TransactionHistoryAdapter;
import com.ns.alerts.Alerts;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.view.CustomProgressBar;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransactionHistoryFragment extends BaseFragmentTHP {

    private String mFrom;

    private RecyclerViewPullToRefresh mRecyclerViewPullToRefresh;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_transaction_history;
    }

    public static TransactionHistoryFragment getInstance(String from) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
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

        mRecyclerViewPullToRefresh = view.findViewById(R.id.recyclerViewPullToRefresh);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        loadData();

    }


    private void loadData() {
        mRecyclerViewPullToRefresh.showProgressBar();
        mDisposable.add(ApiManager.getUserProfile(getActivity())
                .subscribe(userProfile ->
                    ApiManager.getTxnHistory(userProfile.getUserId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(txnDataBeans -> {
                                TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(txnDataBeans, "HISTORY");
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
