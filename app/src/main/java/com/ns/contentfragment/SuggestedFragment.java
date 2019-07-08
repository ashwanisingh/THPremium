package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.alerts.Alerts;
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

public class SuggestedFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private LinearLayout emptyLayout;
    private AppTabContentAdapter mRecyclerAdapter;



    public static SuggestedFragment getInstance(String userId) {
        SuggestedFragment fragment = new SuggestedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_trending;
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

        mPullToRefreshLayout = view.findViewById(R.id.recyclerView);
        emptyLayout = view.findViewById(R.id.emptyLayout);

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), NetConstants.RECO_suggested, mUserId);

        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        mPullToRefreshLayout.setTryAgainBtnClickListener(this);

        mPullToRefreshLayout.showProgressBar();

        // Pull To Refresh Listener
        registerPullToRefresh();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mIsVisible && mRecyclerAdapter!= null && mRecyclerAdapter.getItemCount() == 0) {
            loadData();
        }
        else if(mIsVisible && getView() != null && mRecyclerAdapter != null) {
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
                    NetConstants.RECO_suggested, ""+mSize, BuildConfig.SITEID);
        } else {
            observable = ApiManager.getRecommendationFromDB(getActivity(), NetConstants.RECO_suggested);
        }

        mDisposable.add(
                observable
                        .map(value->{
                            List<AppTabContentModel> content = new ArrayList<>();
                            for(RecoBean bean : value) {
                                AppTabContentModel model = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);
                                model.setBean(bean);
                                content.add(model);
                            }
                            return content;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            mRecyclerAdapter.addData(value);
                        }, throwable -> {
                            if (throwable instanceof HttpException || throwable instanceof ConnectException
                                    || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException
                                    || throwable instanceof NullPointerException) {
                                loadData(false);
                            }

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
    private void showEmptyLayout() {
        if(mRecyclerAdapter.getItemCount() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.setVisibility(View.GONE);
        }
    }
}
