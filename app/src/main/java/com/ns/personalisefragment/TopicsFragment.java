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
//    private TextView mErrorText;
//    private ProgressBar mProgressBar;
//    private LinearLayout mProgressContainer;
    private TopicsCitiesRecyclerAdapter mRecyclerAdapter;

    private THPPersonaliseActivity mActivity;

    private THPPersonaliseItemClickListener mPersonaliseItemClickListener;

    public void setPersonaliseItemClickListener(THPPersonaliseItemClickListener personaliseItemClickListener) {
        mPersonaliseItemClickListener = personaliseItemClickListener;
    }

    public static TopicsFragment getInstance(ArrayList<PersonaliseModel> data, String from) {
        TopicsFragment fragment = new TopicsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        bundle.putString("From", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<PersonaliseModel> mData;
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
            mData = getArguments().getParcelableArrayList("data");
            mFrom = getArguments().getString("From");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullToRefreshLayout = view.findViewById(R.id.recyclerView_topics);
//        mProgressBar =  view.findViewById(R.id.section_progress);
//        mProgressContainer =  view.findViewById(R.id.progress_container);
//        mErrorText =  view.findViewById(R.id.error_text);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        mPullToRefreshLayout.getRecyclerView().setLayoutManager(glm);

        mRecyclerAdapter = new TopicsCitiesRecyclerAdapter(mData, mFrom, this);

        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        if (mData != null && mData.size() > 0) {
          //  mProgressContainer.setVisibility(View.GONE);
        } else {
           // mProgressBar.setVisibility(View.GONE);
          //  mErrorText.setVisibility(View.VISIBLE);
        }

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
