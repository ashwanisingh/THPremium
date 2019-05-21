package com.ns.contentfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.activity.THP_DetailActivity;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.alerts.Alerts;
import com.ns.callbacks.FragmentTools;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.model.AppTabContentModel;
import com.ns.model.ToolbarCallModel;
import com.ns.thpremium.R;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class THP_DetailFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener, FragmentTools {

    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private AppTabContentAdapter mRecyclerAdapter;
    private RecoBean mRecoBean;
    private String mArticleId;

    protected final CompositeDisposable mDisposable = new CompositeDisposable();


    public static THP_DetailFragment getInstance(RecoBean recoBean, String articleId) {
        THP_DetailFragment fragment = new THP_DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("RecoBean", recoBean);
        bundle.putString("articleId", articleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof THP_DetailActivity) {
            mActivity = (THP_DetailActivity) context;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof THP_DetailActivity) {
            mActivity = (THP_DetailActivity) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mRecoBean = getArguments().getParcelable("RecoBean");
            mArticleId = getArguments().getString("articleId");
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_detail_thp_1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPullToRefreshLayout = view.findViewById(R.id.recyclerView);

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), "THP_DetailFragment");

        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        mPullToRefreshLayout.setTryAgainBtnClickListener(this);

        mPullToRefreshLayout.enablePullToRefresh(false);


        // Pull To Refresh Listener
        registerPullToRefresh();

        AppTabContentModel bannerModel = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DETAIL_IMAGE_BANNER);
        bannerModel.setBean(mRecoBean);

        mRecyclerAdapter.addData(bannerModel);

        AppTabContentModel descriptionModel = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DETAIL_DESCRIPTION_WEBVIEW);
        descriptionModel.setBean(mRecoBean);

        mRecyclerAdapter.addData(descriptionModel);

        loadDataFromServer();

        if(mActivity != null && mIsVisible) {
            // Set Toolbar Item Click Listener
            mActivity.setOnFragmentTools(this);
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(mActivity != null && mIsVisible) {
            // Set Toolbar Item Click Listener
            mActivity.setOnFragmentTools(this);

            // Checking Visible Article is bookmarked or not.
            isExistInBookmark(mRecoBean.getArticleId());
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


        });
    }


    @Override
    public void tryAgainBtnClick() {
        mPullToRefreshLayout.showProgressBar();

    }

    private void loadDataFromServer() {
        Observable<RecoBean> observable =  ApiManager.articleDetailFromServer(getActivity(), mArticleId);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecoBean>() {
                               @Override
                               public void accept(RecoBean recoBean) throws Exception {
                                   mRecyclerAdapter.replaceData(recoBean, 0);
                                   mRecyclerAdapter.replaceData(recoBean, 1);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("", "");
                            }
                        });
    }


    @Override
    public void onBackClickListener() {

    }

    @Override
    public void onSearchClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onShareClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onCreateBookmarkClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onRemoveBookmarkClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onFontSizeClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onCommentClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onTTSPlayClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onTTSStopClickListener(ToolbarCallModel toolbarCallModel) {

    }


    /**
     * Checks, Visible Article is bookmarked or not.
     * @param aid
     */
    private void isExistInBookmark(String aid) {
        ApiManager.isExistInBookmark(getActivity(), aid)
                .subscribe(new Consumer<Boolean>() {
                               @Override
                               public void accept(Boolean aBoolean) throws Exception {
                                   mActivity.getToolbar().setIsBookmarked(aBoolean);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        })
        ;
    }
}
