package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.load.HttpException;
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
import com.ns.utils.THPConstants;
import com.ns.utils.TextUtil;
import com.ns.view.CustomTextView;
import com.ns.view.RecyclerViewPullToRefresh;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BriefcaseFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener, OnEditionBtnClickListener {

    private RecyclerViewPullToRefresh mPullToRefreshLayout;
    private LinearLayout emptyLayout;
    private AppTabContentAdapter mRecyclerAdapter;
    private CustomTextView yourEditionFor_Txt;
    private CustomTextView dateBtn_Txt;
    private CustomTextView userName_Txt;
    private CustomTextView editionBtn_Txt;
    private String mBreifingType = NetConstants.BREIFING_ALL;
    private AppTabContentModel mProfileNameModel;

    public static BriefcaseFragment getInstance(String userId) {
        BriefcaseFragment fragment = new BriefcaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_briefcase;
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
        yourEditionFor_Txt = view.findViewById(R.id.yourEditionFor_Txt);
        dateBtn_Txt = view.findViewById(R.id.dateBtn_Txt);
        userName_Txt = view.findViewById(R.id.userName_Txt);
        editionBtn_Txt = view.findViewById(R.id.editionBtn_Txt);

        mRecyclerAdapter = new AppTabContentAdapter(new ArrayList<>(), mBreifingType, mUserId);
        mRecyclerAdapter.setOnEditionBtnClickListener(this::OnEditionBtnClickListener);
        mPullToRefreshLayout.setDataAdapter(mRecyclerAdapter);

        mPullToRefreshLayout.setTryAgainBtnClickListener(this);

        mPullToRefreshLayout.showProgressBar();


        // Pull To Refresh Listener
        registerPullToRefresh();

        // Edition Btn Click Listener
        /*editionBtn_Txt.setOnClickListener(v->{
            EditionOptionFragment fragment = EditionOptionFragment.getInstance();
            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnEditionOptionClickListener(value -> {
                editionBtn_Txt.setText(value);

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
        });*/

        // Date Btn Click Listener
        dateBtn_Txt.setOnClickListener(v->{
            CalendarFragment fragment = CalendarFragment.getInstance();

            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnCalendarDateClickListener(date -> {
                SimpleDateFormat df = new SimpleDateFormat(THPConstants.date_dd_MM_yyyy);
                dateBtn_Txt.setText(df.format(date));

                // Clearing Calendar Fragment
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            });
        });

        if(mIsVisible) {
            //loadData();

            // Shows user name
            ApiManager.getUserProfile(getActivity())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userProfile -> {
                        RecoBean profileRecoBean = null;

                        if(userProfile != null && !TextUtils.isEmpty(userProfile.getFullName())) {
                            userName_Txt.setText("Hi "+userProfile.getFullName());
                            profileRecoBean = new RecoBean();
                            profileRecoBean.setTitle("Hi "+userProfile.getFullName());
                        } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getEmailId())) {
                            userName_Txt.setText(userProfile.getEmailId());
                            profileRecoBean = new RecoBean();
                            profileRecoBean.setTitle(userProfile.getEmailId());
                        } else if(userProfile != null && !TextUtils.isEmpty(userProfile.getContact())) {
                            userName_Txt.setText(userProfile.getContact());
                            profileRecoBean = new RecoBean();
                            profileRecoBean.setTitle(userProfile.getContact());
                        } else {
                            userName_Txt.setVisibility(View.GONE);
                        }

                        // Create Header Model
                        createHeaderModel(profileRecoBean);

                        loadData();
                    });
        }

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
        if (isOnline) {
            observable = ApiManager.getBreifingFromServer(getActivity(), BuildConfig.BREIGINE_URL);
        } else {
            observable = ApiManager.getBreifingFromDB(getActivity(), mBreifingType);
        }
        mDisposable.add(
                observable
                        .map(value->{
                            List<AppTabContentModel> content = new ArrayList<>();
                            addHeaderModel(content);
                            for(RecoBean bean : value) {
                                AppTabContentModel model = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);
                                model.setBean(bean);
                                content.add(model);
                            }

                            return content;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            mRecyclerAdapter.setFrom(mBreifingType);
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
    private void showEmptyLayout() {
        if(mRecyclerAdapter.getItemCount() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.setVisibility(View.GONE);
        }
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


    private void createHeaderModel(RecoBean profileRecoBean) {
        if(profileRecoBean != null) {
            profileRecoBean.setSectionName("All Editions");
            mProfileNameModel = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE_HEADER);
            mProfileNameModel.setBean(profileRecoBean);
        }
    }

    private void addHeaderModel(List<AppTabContentModel> content) {
        if(mProfileNameModel != null) {
            content.add(mProfileNameModel);
        }
    }
}
