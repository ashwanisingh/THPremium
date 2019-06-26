package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.netoperation.net.ApiManager;
import com.ns.adapter.SubscriptionPackAdapter;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SubscriptionPackFragment extends BaseFragmentTHP {

    private RecyclerView mRecyclerView;

    private SubscriptionPackAdapter mAdapter;

    private String mFrom;

    public static SubscriptionPackFragment getInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        SubscriptionPackFragment fragment = new SubscriptionPackFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_pack;
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
        mRecyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);

        loadData();

    }

    /**
     * Load User Plan Info
     */
    private void loadData() {
        mDisposable.add(ApiManager.getUserProfile(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                   ApiManager.getUserPlanInfo(userProfile.getUserId(), BuildConfig.SITEID)
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(planInfoList->{
                               mAdapter = new SubscriptionPackAdapter(mFrom, planInfoList);
                               mRecyclerView.setAdapter(mAdapter);
                           }, throwable -> {

                           }, ()->{

                           });
                }));
    }




}
