package com.ns.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ns.adapter.AppTabContentAdapter;
import com.ns.thpremium.R;


public class RecyclerViewPullToRefresh extends FrameLayout  {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CustomTextView mTryAgainBtn;
    private ProgressBar mProgressBar;

    private LinearLayout networkIndicationLayout;

    private boolean isLoading;
    private boolean isLastPage;

    private TryAgainBtnClickListener mTryAgainBtnClickListener;
    private LinearLayoutManager llm;

    public void setTryAgainBtnClickListener(TryAgainBtnClickListener tryAgainBtnClickListener) {
        mTryAgainBtnClickListener = tryAgainBtnClickListener;
    }

    public RecyclerViewPullToRefresh(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RecyclerViewPullToRefresh(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewPullToRefresh(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.layout_recycler_pull_refresh, this, true);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mRecyclerView = findViewById(R.id.recyclerViewPull);
        mTryAgainBtn = findViewById(R.id.tryAgainButton);
        mProgressBar = findViewById(R.id.progressBar);
        networkIndicationLayout = findViewById(R.id.networkIndicationLayout);

        enablePullToRefresh(true);

        // Setting Recycler Layout Manager
        llm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);

        // Try Again Click Listener
        mTryAgainBtn.setOnClickListener(v-> {
            if (mTryAgainBtnClickListener != null) {
                mTryAgainBtnClickListener.tryAgainBtnClick();
            }
        });

    }


    public void showProgressBar() {
        mProgressBar.setVisibility(VISIBLE);
        mTryAgainBtn.setVisibility(GONE);
    }

    public void showTryAgainBtn() {
        mProgressBar.setVisibility(GONE);
        mTryAgainBtn.setVisibility(VISIBLE);
        mTryAgainBtn.setText(R.string.try_again);
    }

    public void showTryAgainBtn(String tryAgainMsg) {
        mProgressBar.setVisibility(GONE);
        mTryAgainBtn.setVisibility(VISIBLE);
        mTryAgainBtn.setText(tryAgainMsg);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(GONE);
        mTryAgainBtn.setVisibility(GONE);
    }


    public void enablePullToRefresh(boolean isEnable) {
        mSwipeRefreshLayout.setEnabled(isEnable);
    }

    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public interface TryAgainBtnClickListener {
        void tryAgainBtnClick();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public LinearLayoutManager getLlm() {
        return llm;
    }

    public void setGridLayoutManager() {
        llm = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(llm);
    }

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }


    public void setDataAdapter(AppTabContentAdapter adapter) {
            if(mRecyclerView != null && adapter != null) {
                mRecyclerView.setAdapter(adapter);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void showNetworkFailMessage() {
        networkIndicationLayout.setVisibility(VISIBLE);
    }

    public void hideNetworkFailMessage() {
        networkIndicationLayout.setVisibility(GONE);
    }


}
