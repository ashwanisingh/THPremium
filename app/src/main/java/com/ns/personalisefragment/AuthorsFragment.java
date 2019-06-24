package com.ns.personalisefragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.activity.THPPersonaliseActivity;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.adapter.AuthorsRecyclerAdapter;
import com.ns.adapter.SubscriptionPackAdapter;
import com.ns.alerts.Alerts;
import com.ns.callbacks.THPPersonaliseItemClickListener;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.view.RecyclerViewPullToRefresh;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthorsFragment extends BaseFragmentTHP implements THPPersonaliseItemClickListener {

    private RecyclerView mRecyclerView;
//    private TextView mErrorText;
//    private ProgressBar mProgressBar;
//    private LinearLayout mProgressContainer;
    private AuthorsRecyclerAdapter mAdapter;

    private THPPersonaliseActivity mActivity;

    private THPPersonaliseItemClickListener mPersonaliseItemClickListener;

    public void setPersonaliseItemClickListener(THPPersonaliseItemClickListener personaliseItemClickListener) {
        mPersonaliseItemClickListener = personaliseItemClickListener;
    }


    public static AuthorsFragment getInstance(ArrayList<PersonaliseModel> data, String frgamentName) {
        AuthorsFragment fragment = new AuthorsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        bundle.putString("From", frgamentName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_authors;
    }

    private ArrayList<PersonaliseModel> mData;
    private String mFrom;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (THPPersonaliseActivity) context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (THPPersonaliseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity()!=null) {
            mData = getArguments().getParcelableArrayList("data");
            mFrom = getArguments().getString("From");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);
//        mProgressBar =  view.findViewById(R.id.section_progress);
//        mProgressContainer =  view.findViewById(R.id.progress_container);
//        mErrorText =  view.findViewById(R.id.error_text);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mAdapter=new AuthorsRecyclerAdapter(mData, mFrom, this);
        mRecyclerView.setAdapter(mAdapter);
//
//        if (mData != null && mData.size() > 0) {
//            mProgressContainer.setVisibility(View.GONE);
//        } else {
//            mProgressBar.setVisibility(View.GONE);
//            mErrorText.setVisibility(View.VISIBLE);
//        }

        if(mIsVisible) {
            setPersonaliseItemClickListener(mActivity);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mIsVisible) {
            setPersonaliseItemClickListener(mActivity);
        }
    }

    @Override
    public void personaliseItemClick(PersonaliseModel model, String from) {
    if(mActivity != null) {
        mFrom=from;
        mActivity.personaliseItemClick(model, mFrom);
    }

    }


}