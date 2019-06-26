package com.ns.personalisefragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.PersonaliseDetails;
import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.activity.THPPersonaliseActivity;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.adapter.ImagePagerAdapter;
import com.ns.adapter.TopicsCitiesRecyclerAdapter;
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

public class TopicsFragment extends BaseFragmentTHP implements THPPersonaliseItemClickListener{

    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private TopicsCitiesRecyclerAdapter mRecyclerAdapter;

    private THPPersonaliseActivity mActivity;

    private THPPersonaliseItemClickListener mPersonaliseItemClickListener;

    public void setPersonaliseItemClickListener(THPPersonaliseItemClickListener personaliseItemClickListener) {
        mPersonaliseItemClickListener = personaliseItemClickListener;
    }

    public static TopicsFragment getInstance(PersonaliseDetails data, String from) {
        TopicsFragment fragment = new TopicsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putString("From", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PersonaliseDetails mData;
    private String mFrom;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_topics;
    }

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
        if(getArguments()!=null) {
            mData = getArguments().getParcelable("data");
            mFrom = getArguments().getString("From");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullToRefreshLayout = view.findViewById(R.id.recyclerView_topics);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        mPullToRefreshLayout.getRecyclerView().setLayoutManager(glm);

        mRecyclerAdapter = new TopicsCitiesRecyclerAdapter(mData, mFrom, this);
        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);
        mPullToRefreshLayout.enablePullToRefresh(false);

        if(mIsVisible) {
            setPersonaliseItemClickListener(mActivity);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mIsVisible){
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
