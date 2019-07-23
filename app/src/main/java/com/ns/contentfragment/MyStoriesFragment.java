package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.alerts.Alerts;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.view.CustomTextView;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyStoriesFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private CustomTextView recentStoriesCount_Txt;
    private CustomTextView userName_Txt;
    private CustomTextView recentBtn_Txt;
    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private AppTabContentAdapter mRecyclerAdapter;

    public static MyStoriesFragment getInstance(String userId) {
        MyStoriesFragment fragment = new MyStoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mUserId = getArguments().getString("userId");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName_Txt = view.findViewById(R.id.userName_Txt);
        recentStoriesCount_Txt = view.findViewById(R.id.recentStoriesCount_Txt);
        recentBtn_Txt = view.findViewById(R.id.recentBtn_Txt);
        mPullToRefreshLayout = view.findViewById(R.id.recyclerView);

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), NetConstants.RECO_personalised, mUserId);

        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        mPullToRefreshLayout.setTryAgainBtnClickListener(this);

        mPullToRefreshLayout.showProgressBar();

        if(mIsVisible) {
            loadData();
        }

        // Pull To Refresh Listener
        registerPullToRefresh();

        loadUserProfile();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(mIsVisible && (mRecyclerAdapter == null || mRecyclerAdapter.getItemCount() == 0)) {
            loadData();
        }
        else if (mIsVisible && getView() != null && mRecyclerAdapter != null) {
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("", "");
        if(mIsVisible && getView() != null && mRecyclerAdapter != null) {
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Loads User Profile Data
     */
    private void loadUserProfile() {
        ApiManager.getUserProfile(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    if(userProfile != null && !TextUtils.isEmpty(userProfile.getFullName())) {
                        userName_Txt.setText("Hi "+userProfile.getFullName().toUpperCase());
                    } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getEmailId())) {
                        userName_Txt.setText("Hi "+userProfile.getEmailId().toUpperCase());
                    } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getContact())) {
                        userName_Txt.setText("Hi "+userProfile.getContact().toUpperCase());
                    } else {
                        userName_Txt.setVisibility(View.GONE);
                    }

                    recentStoriesCount_Txt.setText("Yours personalised stories");
                });
    }

    /**
     * Adding Pull To Refresh Listener
     */
    private void registerPullToRefresh() {
        mPullToRefreshLayout.getSwipeRefreshLayout().setOnRefreshListener(()->{
            if(!mIsOnline) {
                Alerts.showSnackbar(getActivity(), getResources().getString(R.string.please_check_ur_connectivity));
                mPullToRefreshLayout.setRefreshing(false);
                return;
            }
            mPullToRefreshLayout.setRefreshing(true);

            loadData();
        });
    }


    @Override
    public void tryAgainBtnClick() {
        mPullToRefreshLayout.showProgressBar();
        loadData();
    }

    private void loadData() {
        Observable.just("tryAgain")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(val->{
                    loadData(mIsOnline);
                    return "";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void loadData(boolean isOnline) {

        Observable<List<RecoBean>> observable = null;

        if (isOnline) {
            observable = ApiManager.getRecommendationFromServer(getActivity(), mUserId,
                    NetConstants.RECO_personalised, ""+mSize, BuildConfig.SITEID);
        } else {
            observable = ApiManager.getRecommendationFromDB(getActivity(), NetConstants.RECO_personalised);
        }

        mDisposable.add(
                observable
                        .map(value->{
                            List<AppTabContentModel> content = new ArrayList<>();
                            for(RecoBean bean : value) {
                                AppTabContentModel model = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);
                                model.setBean(bean);
                                content.add(model);
                            }
                            return content;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            mRecyclerAdapter.setData(value);
                        }, throwable -> {
                            /*if (throwable instanceof HttpException || throwable instanceof ConnectException
                                    || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {

                            }*/

                            loadData(false);

                            mPullToRefreshLayout.hideProgressBar();
                            mPullToRefreshLayout.setRefreshing(false);

                        }, () -> {

                            mPullToRefreshLayout.hideProgressBar();
                            mPullToRefreshLayout.setRefreshing(false);

                            // Showing Empty Msg.
                            if (mRecyclerAdapter != null && mRecyclerAdapter.getItemCount() == 0) {
                                mPullToRefreshLayout.showTryAgainBtn("Please Try Again.");
                            }

                        }));

    }


}
