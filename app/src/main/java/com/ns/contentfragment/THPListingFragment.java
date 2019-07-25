package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.alerts.Alerts;
import com.ns.callbacks.OnEditionBtnClickListener;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.FragmentUtil;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class THPListingFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener, OnEditionBtnClickListener {

    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private AppTabContentAdapter mRecyclerAdapter;
    private String mBreifingType = NetConstants.BREIFING_ALL;
    private AppTabContentModel mProfileNameModel;
    String mFrom;

    public static THPListingFragment getInstance(String userId, String from) {
        THPListingFragment fragment = new THPListingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("from", from);
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
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPullToRefreshLayout = view.findViewById(R.id.recyclerView);

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), mFrom, mUserId);
        mRecyclerAdapter.setOnEditionBtnClickListener(this::OnEditionBtnClickListener);
        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        mPullToRefreshLayout.setTryAgainBtnClickListener(this);

        mPullToRefreshLayout.showProgressBar();


        // Pull To Refresh Listener
        registerPullToRefresh();



            //loadData();

            // Shows user name
            ApiManager.getUserProfile(getActivity())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userProfile -> {
                        String title = "";

                        if(userProfile != null && !TextUtils.isEmpty(userProfile.getFullName())) {
                            title = "Hi "+userProfile.getFullName();
                        } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getEmailId())) {
                            title = userProfile.getEmailId();
                        } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getContact())) {
                            title = userProfile.getContact();
                        } else {

                        }

                        // Create Header Model
                        createHeaderModel(title);

                        loadData();
                    });

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

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


    private void loadData(boolean isOnline ) {
        Observable<List<RecoBean>> observable = null;
        if(mFrom.equalsIgnoreCase(NetConstants.BREIFING_ALL)) {
            if (isOnline) {
                observable = ApiManager.getBreifingFromServer(getActivity(), BuildConfig.BREIGINE_URL);
            } else {
                observable = ApiManager.getBreifingFromDB(getActivity(), mBreifingType);
            }
        }
        else {
            if (isOnline) {
                observable = ApiManager.getRecommendationFromServer(getActivity(), mUserId,
                        mFrom, ""+mSize, BuildConfig.SITEID);
            } else {
                observable = ApiManager.getRecommendationFromDB(getActivity(), mFrom);
            }
        }


        mDisposable.add(
                observable
                        .map(value->{
                            List<AppTabContentModel> content = new ArrayList<>();
                            addHeaderModel(content);
                            int viewType = BaseRecyclerViewAdapter.VT_DASHBOARD;
                            if(isBriefingPage()) {
                                viewType = BaseRecyclerViewAdapter.VT_BRIEFCASE;
                            } else {
                                viewType = BaseRecyclerViewAdapter.VT_DASHBOARD;
                            }
                            for(RecoBean bean : value) {
                                AppTabContentModel model = new AppTabContentModel(viewType);
                                model.setBean(bean);
                                content.add(model);
                            }

                            return content;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            mRecyclerAdapter.setFrom(mFrom);
                            mRecyclerAdapter.setData(value);
                        }, throwable -> {
                            loadData(false);
                            mPullToRefreshLayout.hideProgressBar();
                            mPullToRefreshLayout.setRefreshing(false);

                        }, () -> {

                            mPullToRefreshLayout.hideProgressBar();
                            mPullToRefreshLayout.setRefreshing(false);

                            // Showing Empty Msg.
                            if (mRecyclerAdapter != null && mRecyclerAdapter.getItemCount() == 1) {
                                mPullToRefreshLayout.showTryAgainBtn("Please Try Again.");
                            }

                        }));

    }


    @Override
    public void OnEditionBtnClickListener() {
        EditionOptionFragment fragment = EditionOptionFragment.getInstance();
        FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

        fragment.setOnEditionOptionClickListener(value -> {
            if(mProfileNameModel != null) {
                mProfileNameModel.getBean().setSectionName(value);
            }
            // Clearing Edition option Fragment
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            if(value.equalsIgnoreCase("All Editions")) {
                mBreifingType = NetConstants.BREIFING_ALL;
            } else if(value.equalsIgnoreCase("Morning Editions")) {
                mBreifingType = NetConstants.BREIFING_MORNING;
            } else if(value.equalsIgnoreCase("Noon Editions")) {
                mBreifingType = NetConstants.BREIFING_NOON;
            } else if(value.equalsIgnoreCase("Evening Editions")) {
                mBreifingType = NetConstants.BREIFING_EVENING;
            }

            loadData(false);

        });
    }

    private boolean isBriefingPage() {
        if(mFrom.equalsIgnoreCase(NetConstants.BREIFING_ALL) || mFrom.equalsIgnoreCase(NetConstants.BREIFING_MORNING)
                || mFrom.equalsIgnoreCase(NetConstants.BREIFING_NOON) || mFrom.equalsIgnoreCase(NetConstants.BREIFING_EVENING)) {
            return true;
        }
        return false;
    }


    private void createHeaderModel(String title) {
        RecoBean profileRecoBean = new RecoBean();
        if (isBriefingPage()) {
            profileRecoBean.setSectionName("All Editions");
        } else if (mFrom.equalsIgnoreCase(NetConstants.RECO_personalised)) {
            profileRecoBean.setSectionName("Yours personalised stories");
        } else if (mFrom.equalsIgnoreCase(NetConstants.RECO_suggested)) {
            profileRecoBean.setSectionName("Your suggested stories");
            title = "Your suggested stories";
        } else if (mFrom.equalsIgnoreCase(NetConstants.RECO_trending)) {
            profileRecoBean.setSectionName("Trending now");
            title = "Trending now";
        }
        profileRecoBean.setTitle(title);
        mProfileNameModel = new AppTabContentModel(BaseRecyclerViewAdapter.VT_HEADER);
        mProfileNameModel.setBean(profileRecoBean);
    }


    private void addHeaderModel(List<AppTabContentModel> content) {
        if(mProfileNameModel != null) {
            content.add(mProfileNameModel);
        }
    }
}
