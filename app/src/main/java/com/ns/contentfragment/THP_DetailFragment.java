package com.ns.contentfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Api;
import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.UserPref;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.activity.THP_DetailActivity;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.alerts.Alerts;
import com.ns.callbacks.FragmentTools;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.model.AppTabContentModel;
import com.ns.model.ToolbarCallModel;
import com.ns.thpremium.R;
import com.ns.tts.TTSCallbacks;
import com.ns.tts.TTSManager;
import com.ns.utils.CommonUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.THPConstants;
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


    public static THP_DetailFragment getInstance(RecoBean recoBean, String articleId, String userId) {
        THP_DetailFragment fragment = new THP_DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("RecoBean", recoBean);
        bundle.putString("articleId", articleId);
        bundle.putString("userId", userId);
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
            mUserId = getArguments().getString("userId");
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

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), "THP_DetailFragment", mUserId);

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

            // Checking Visible Article is bookmarked or not.
            isExistInBookmark(mArticleId);
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
        mDisposable.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(recoBean->{
                            mRecyclerAdapter.replaceData(recoBean, 0);
                            mRecyclerAdapter.replaceData(recoBean, 1);
                        },
                        throwable->{
                            Log.i("", "");
                        })
        );
    }


    @Override
    public void onBackClickListener() {

    }

    @Override
    public void onSearchClickListener(ToolbarCallModel toolbarCallModel) {

    }

    @Override
    public void onShareClickListener(ToolbarCallModel toolbarCallModel) {
        CommonUtil.shareArticle(getActivity(), mRecoBean);
    }

    @Override
    public void onCreateBookmarkClickListener(ToolbarCallModel toolbarCallModel) {
        ApiManager.createBookmark(getActivity(), mRecoBean)
                .subscribe(value->
                        mActivity.getToolbar().setIsBookmarked((Boolean)value)
                );
    }

    @Override
    public void onRemoveBookmarkClickListener(ToolbarCallModel toolbarCallModel) {
        ApiManager.createUnBookmark(getActivity(), mArticleId)
                .subscribe(value->
                        mActivity.getToolbar().setIsBookmarked((Boolean)value)
                );
    }

    @Override
    public void onFontSizeClickListener(ToolbarCallModel toolbarCallModel) {
        final int currentSize = UserPref.getInstance(getActivity()).getDescriptionSize();
        switch (currentSize) {
            case THPConstants.DESCRIPTION_SMALL:
                UserPref.getInstance(getActivity()).setDescriptionSize(THPConstants.DESCRIPTION_NORMAL);
                break;

            case THPConstants.DESCRIPTION_NORMAL:
                UserPref.getInstance(getActivity()).setDescriptionSize(THPConstants.DESCRIPTION_LARGE);
                break;

            case THPConstants.DESCRIPTION_LARGE:
                UserPref.getInstance(getActivity()).setDescriptionSize(THPConstants.DESCRIPTION_LARGEST);
                break;

            case THPConstants.DESCRIPTION_LARGEST:
                UserPref.getInstance(getActivity()).setDescriptionSize(THPConstants.DESCRIPTION_SMALL);
                break;
        }

        updateDescriptionTextSize();
    }

    @Override
    public void onCommentClickListener(ToolbarCallModel toolbarCallModel) {
        IntentUtil.openCommentActivity(getActivity(), mRecoBean);
    }

    @Override
    public void onTTSPlayClickListener(ToolbarCallModel toolbarCallModel) {
        TTSManager ttsManager2 = TTSManager.getInstance();
        if(ttsManager2.isTTSInitialized()) {
            ttsManager2.speakSpeech(mRecoBean);
            return;
        }
        ttsManager2.init(getActivity(),  new TTSCallbacks() {
            @Override
            public boolean onTTSInitialized() {
                TTSManager.getInstance().speakSpeech(mRecoBean);
                return false;
            }

            @Override
            public boolean isPlaying() {
                return false;
            }

            @Override
            public void onTTSError(int errorCode) {
                switch (errorCode) {
                    case TextToSpeech.LANG_MISSING_DATA:
                        Alerts.showToast(getActivity(), R.string.language_not_available);
                        break;
                    case TextToSpeech.LANG_NOT_SUPPORTED:
                        Alerts.showToast(getActivity(), R.string.language_not_available);
                        break;
                    case 1000:
                    case 1001:
                        Alerts.showToast(getActivity(), R.string.language_missing_data);
                        IntentUtil.installVoiceData(getActivity());
                        break;

                }
                mActivity.getToolbar().showTTSPlayView(UserPref.getInstance(getActivity()).isLanguageSupportTTS());
            }

            @Override
            public void onTTSPlayStarted(int loopCount) {
                if(loopCount == 0) {
                    mActivity.getToolbar().showTTSPauseView(UserPref.getInstance(getActivity()).isLanguageSupportTTS());
                }
            }
        });
    }

    @Override
    public void onTTSStopClickListener(ToolbarCallModel toolbarCallModel) {
        TTSManager.getInstance().stopTTS();
        mActivity.getToolbar().showTTSPlayView(UserPref.getInstance(getActivity()).isLanguageSupportTTS());
    }

    /**
     * Updates Description WebView Text Size
     */
    private void updateDescriptionTextSize() {
        if(mRecyclerAdapter != null) {
            int descriptionSize = UserPref.getInstance(getActivity()).getDescriptionSize();
            if(mRecyclerAdapter.getLastDescriptionTextSize() != descriptionSize) {
                mRecyclerAdapter.notifyItemChanged(mRecyclerAdapter.getDescriptionItemPosition());
            }
        }
    }


    /**
     * Checks, Visible Article is bookmarked or not.
     * @param aid
     */
    private void isExistInBookmark(String aid) {
        mDisposable.add(
                ApiManager.isExistInBookmark(getActivity(), aid)
                .subscribe(
                        bool-> mActivity.getToolbar().setIsBookmarked((Boolean) bool),
                        throwable->{
                            Log.i("", "");
                        })
        );
    }
}
