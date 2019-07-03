package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.adapter.DetailPagerAdapter;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.view.ViewPagerScroller;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class THP_DetailPagerFragment extends BaseFragmentTHP {

    private ViewPager mViewPager;

    private String mFrom;
    private int mClickedPosition;
    private String mArticleUrl;
    private RecoBean mRecoBean;
    private String mArticleId;

    private DetailPagerAdapter mSectionsPagerAdapter;


    public static final THP_DetailPagerFragment getInstance(String articleId,
                                                            int clickedPosition, String from, String userId) {
        THP_DetailPagerFragment fragment = new THP_DetailPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("clickedPosition", clickedPosition);
        bundle.putString("articleId", articleId);
        bundle.putString("from", from);
        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_pager_detail_thp;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mRecoBean = getArguments().getParcelable("RecoBean");
            mClickedPosition = getArguments().getInt("clickedPosition");
            mArticleId = getArguments().getString("articleId");
            mArticleUrl = getArguments().getString("articleUrl");
            mFrom = getArguments().getString("from");
            mUserId = getArguments().getString("userId");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = view.findViewById(R.id.viewPager);


        // This is smooth scroll of ViewPager
        smoothPagerScroll();

        // ViewPager Adapter Initialisation and Assiging
        mSectionsPagerAdapter = new DetailPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        loadData();
    }


    private void setCurrentPage(int position, boolean smoothScroll) {
        mViewPager.setCurrentItem(position, smoothScroll);
    }


    /**
     * This is ViewPager Page Scroll Animation
     */
    private void smoothPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(mViewPager, new ViewPagerScroller(getActivity(),
                    new LinearInterpolator(), 250));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadData() {
        Observable<List<RecoBean>> observable = null;
        if(mFrom.equalsIgnoreCase(NetConstants.BREIFING_ALL) || mFrom.equalsIgnoreCase(NetConstants.BREIFING_EVENING)
        || mFrom.equalsIgnoreCase(NetConstants.BREIFING_NOON) || mFrom.equalsIgnoreCase(NetConstants.BREIFING_MORNING)) {
            observable = ApiManager.getBreifingFromDB(getActivity(), mFrom);
        }
        else {
            observable = ApiManager.getRecommendationFromDB(getActivity(), mFrom);
        }

        mDisposable.add(
                observable.map(value->{
                    return value;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {

                    for(RecoBean model : value) {
                        mSectionsPagerAdapter.addFragment(THP_DetailFragment.getInstance(model, model.getArticleId(), mUserId, mFrom));
                    }

                    // To Check the selected article Index
                    if (mArticleId != null) {
                        RecoBean bean = new RecoBean();
                        bean.setArticleId(mArticleId);

                        int index = value.indexOf(bean);
                        if (index != -1) {
                            mClickedPosition = index;
                        }
                    }

                    // Setting current position of ViewPager
                    setCurrentPage(mClickedPosition, false);

                }, throwable -> {
                    if (throwable instanceof HttpException || throwable instanceof ConnectException
                            || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                        // TODO,
                    }



                }, () -> {



                }));
    }

}
